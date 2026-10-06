package com.tasktracker.mapper;

public interface Mapper<FROM, TO> {

    TO map(FROM object);
}
