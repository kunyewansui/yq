package com.xiaosuokeji.server.manager.intf;

import java.lang.reflect.Type;

/**
 * Created by gustinlau on 10/31/17.
 */
public interface GsonService {

    String toJson(Object src);

    <T> T fromJson(String json, Class<T> classOfT);

    <T> T fromJson(String json, Type typeOfT);
}
