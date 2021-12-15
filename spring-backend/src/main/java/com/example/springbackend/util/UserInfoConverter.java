package com.example.springbackend.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserInfoConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute.replaceAll("[() .-]", "");
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
