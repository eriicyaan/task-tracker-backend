package com.mapper;

import com.dto.UserReadDto;
import com.entity.User;
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
