package com.apprikot.mathurat.model.parser;

public class NoParser extends BaseParser {
    @Override
    public Object parse(Class responseClass, String json) {
        return json;
    }
}