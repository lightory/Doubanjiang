package net.lightory.doubanjiang.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MappingUtil {
    static private ObjectMapper objectMapper;
    
    public static Object parseObjectFromJSONString(String jsonString, Class<?> clazz) throws JsonParseException, JsonMappingException, IOException {
        return MappingUtil.getObjectMapper().readValue(jsonString, clazz);
    }
    
    private static ObjectMapper getObjectMapper() {
        if (null == MappingUtil.objectMapper) {
            MappingUtil.objectMapper = new ObjectMapper();
            MappingUtil.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return MappingUtil.objectMapper;
    }
    
    private MappingUtil() {
        
    }
}
