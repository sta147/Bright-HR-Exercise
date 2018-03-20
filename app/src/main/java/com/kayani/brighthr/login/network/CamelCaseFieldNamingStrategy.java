package com.kayani.brighthr.login.network;

import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

public class CamelCaseFieldNamingStrategy implements FieldNamingStrategy {
    @Override
    public String translateName(Field f) {
        final String name = f.getName();
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }
}
