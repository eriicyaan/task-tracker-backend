package com.tasktracker.mapper;

import com.tasktracker.dto.UserReadDto;
import com.tasktracker.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .username(object.getUsername())
                .build();
    }
}
