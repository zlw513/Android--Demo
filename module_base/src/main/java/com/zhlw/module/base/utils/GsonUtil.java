package com.zhlw.module.base.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtil {

    private static final String TAG = "GsonUtil";

    private GsonUtil() { }

    private static Gson GsonInstance(){
        return new GsonBuilder().serializeNulls().create();
    }


    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        try{
            gsonString = GsonInstance().toJson(object);
        } catch (Throwable throwable){
            throwable.printStackTrace();
            Log.e(TAG,"GsonString Phase Error Object"+object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        try {
            t = GsonInstance().fromJson(gsonString, cls);
        } catch (Throwable throwable){
            throwable.printStackTrace();
            Log.e(TAG,"GsonToBean Phase Error gsonString"+gsonString);
        }
        return t;
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        try{
            list = GsonInstance().fromJson(gsonString, new TypeToken<List<T>>() {}.getType());
        } catch (Throwable throwable){
            throwable.printStackTrace();
            Log.e(TAG,"GsonToList  Error gsonString "+gsonString);
        }
        return list;
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try{
            list = GsonInstance().fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {}.getType());
        } catch (Throwable throwable){
            throwable.printStackTrace();
            Log.e(TAG,"GsonToListMaps Error  gsonString"+gsonString);
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        try {
            map = GsonInstance().fromJson(gsonString, new TypeToken<Map<String, T>>() {}.getType());
        } catch (Throwable throwable){
            throwable.printStackTrace();
            Log.e(TAG,"GsonToMaps Error  gsonString"+gsonString);
        }
        return map;
    }
}