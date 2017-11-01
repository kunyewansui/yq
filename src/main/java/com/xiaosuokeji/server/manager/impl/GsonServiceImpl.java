package com.xiaosuokeji.server.manager.impl;

import com.google.gson.Gson;
import com.xiaosuokeji.server.manager.intf.GsonService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

/**
 * Created by gustinlau on 10/31/17.
 */
@Service("gsonService")
public class GsonServiceImpl implements GsonService{

    private Gson gson;

    public GsonServiceImpl() {
        gson=new Gson();
    }

    @Override
    public String toJson(Object src) {
        return gson.toJson(src);
    }

    @Override
    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    @Override
    public <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
}
