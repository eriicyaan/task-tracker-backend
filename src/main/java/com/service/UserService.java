package com.service;

import com.dto.UserCreateEditDto;
import com.dto.UserReadDto;
import com.entity.User;
import com.exception.UserNotFoundException;
import com.kafka.events.EmailSendingEvent;
import com.kafka.events.EventType;
import com.mapper.UserCreateEditMapper;
import com.mapper.UserMapper;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserMapper userMapper;
    private final KafkaTemplate<UUID, EmailSendingEvent> kafkaTemplate;

    public UserReadDto findUserByUsername(String username) {

        User user = userRepository.findUserByUsername(username);

        if(user == null) {
            return null;
        }

        return userMapper.map(user);
    }

    public void create(UserCreateEditDto userCreateEditDto) {
        Optional.of(userCreateEditDto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(user -> EmailSendingEvent.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .report(null)
                        .eventType(EventType.USER_CREATED).build()
                )
                .map(user -> kafkaTemplate.send("email-sending-tasks", user.getId(), user))
                .orElseThrow();

    }

    public List<UserReadDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::map)
                .toList();

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.of(userRepository.findUserByUsername(username))
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole()))
                )
                .orElseThrow();
    }

    public UserReadDto findUserById(UUID id) {
        User user = userRepository.findUsersById(id);

        if(user == null) {
            throw new UserNotFoundException("user not found");
        }

        return userMapper.map(user);
    }
}
