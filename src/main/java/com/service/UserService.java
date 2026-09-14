package com.service;

import com.dto.UserCreateEditDto;
import com.dto.UserReadDto;
import com.entity.User;
import com.mapper.UserCreateEditMapper;
import com.mapper.UserMapper;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserMapper userMapper;

    public UserReadDto findUserByEmail(String email) {

        User userByEmail = userRepository.findUserByEmail(email);

        if(userByEmail == null) {
            return null;
        }

        return userMapper.map(userByEmail);
    }

    public UserReadDto create(UserCreateEditDto userCreateEditDto) {
        return Optional.of(userCreateEditDto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userMapper::map)
                .orElseThrow();
    }
}
