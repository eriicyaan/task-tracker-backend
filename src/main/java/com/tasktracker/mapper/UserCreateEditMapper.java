package com.tasktracker.mapper;

import com.tasktracker.dto.UserCreateEditDto;
import com.tasktracker.entity.User;
import com.tasktracker.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateEditDto object) {
        return User.builder()
                .username(object.getUsername())
                .password(passwordEncoder.encode(object.getPassword()))
                .registrationDate(LocalDate.now())
                .role(UserRole.USER)
                .build();
    }
}
