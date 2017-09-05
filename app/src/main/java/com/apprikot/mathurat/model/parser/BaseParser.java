package com.apprikot.mathurat.model.parser;

public abstract class BaseParser {
    protected static final boolean FAIL_ON_UNKNOWN_PROPERTIES = false;

    public abstract Object parse(Class responseClass, String json);
}