package com.example.playlistgeneratorv1.exceptions;

public class EntityLongNotFoundException extends RuntimeException {
    public EntityLongNotFoundException(String type, long id) {
        this(type, "long", String.valueOf(id));
    }

    public EntityLongNotFoundException(String type, String attribute, String value) {
        super(String.format("%s with %s %s not found.", type, attribute, value));
    }

}
