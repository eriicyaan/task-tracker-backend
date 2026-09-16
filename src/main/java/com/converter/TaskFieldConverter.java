package com.converter;

import com.entity.TaskField;
import com.exception.FieldNotValidException;
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
