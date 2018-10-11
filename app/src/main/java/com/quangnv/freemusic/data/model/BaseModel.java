package com.quangnv.freemusic.data.model;

import com.google.gson.Gson;

/**
 * Created by quangnv on 11/10/2018
 */

public class BaseModel implements Cloneable {

    @Override
    public Object clone() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), this.getClass());
    }
}
