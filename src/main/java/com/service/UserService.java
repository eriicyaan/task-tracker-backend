package com.service;

import com.dto.UserCreateEditDto;
import com.dto.UserReadDto;
import com.entity.User;
import com.event.UserCreatedEvent;
import com.mapper.UserCreateEditMapper;
import com.mapper.UserMapper;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserMapper userMapper;
    private final KafkaTemplate<UUID, UserCreatedEvent> kafkaTemplate;

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
                .map(user -> new UserCreatedEvent(user.getId(), user.getUsername()))
                .map(user -> kafkaTemplate.send("user-created-event-topic", user.getId(), user))
                .orElseThrow();

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
}
