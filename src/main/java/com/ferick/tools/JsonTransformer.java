package com.ferick.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonTransformer {

    public static <T> String toJson(T obj) {
        return toJson(obj, false);
    }

    public static <T> String toJson(T obj, boolean isFailOnEmptyBeansDisabled) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (isFailOnEmptyBeansDisabled) {
                mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            }
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        T obj = null;
        try {
            obj = new ObjectMapper().readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
