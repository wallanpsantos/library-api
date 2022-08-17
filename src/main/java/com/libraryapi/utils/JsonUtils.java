package com.libraryapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    public static String toJson(Object object) {
        try {
            return objectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("[ConvertToJson] Error to convert object to json, return an empty object {}", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return objectMapper().readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("[ConvertJsonToObject] Error to convert json to object, return an empty object {}", e.getMessage());
            return null;
        }
    }

    private static ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
