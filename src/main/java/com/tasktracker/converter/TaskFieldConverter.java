package com.tasktracker.converter;

import com.tasktracker.entity.TaskField;
import com.tasktracker.handler.exception.FieldNotValidException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class TaskFieldConverter implements Converter<String, TaskField> {

    @Override
    public TaskField convert(String source) {
        try {
            return TaskField.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new FieldNotValidException("field is not valid");
        }
    }
}
