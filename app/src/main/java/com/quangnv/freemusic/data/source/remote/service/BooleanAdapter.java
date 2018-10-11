package com.quangnv.freemusic.data.source.remote.service;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by quangnv on 11/10/2018
 */

public class BooleanAdapter extends TypeAdapter<Boolean> {
    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value);
    }

    @Override
    public Boolean read(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        switch (peek) {
            case NULL:
                in.nextNull();
                return null;
            case BOOLEAN:
                return in.nextBoolean();
            case NUMBER:
                return in.nextInt() != 0;
            case STRING:
                return Boolean.valueOf(in.nextString());
            default:
                return null;
        }
    }
}
