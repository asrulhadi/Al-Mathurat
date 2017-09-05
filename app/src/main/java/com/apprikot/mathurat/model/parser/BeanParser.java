package com.apprikot.mathurat.model.parser;

import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanParser extends BaseParser {
    @Override
    public Object parse(Class responseClass, String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType type = mapper.getTypeFactory().constructType(responseClass);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, FAIL_ON_UNKNOWN_PROPERTIES);
//            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            return mapper.readValue(json, type);
        } catch (Exception e) {
            Log.w(getClass().getSimpleName(), "Error parsing json");
            // e.printStackTrace();
        }
        return null;
    }
}