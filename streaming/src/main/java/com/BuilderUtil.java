package com;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BuilderUtil {

    private static final ObjectMapper OBJECT_MAPPER_SNAKE_CASE = new ObjectMapper();

    public static ObjectMapper getJsonObjectMapperSnakeCase() {
        return OBJECT_MAPPER_SNAKE_CASE;
    }
}
