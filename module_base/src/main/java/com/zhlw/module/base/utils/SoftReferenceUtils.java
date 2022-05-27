package com.zhlw.module.base.utils;

import android.content.Context;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 减少对SystemService重复创建
 */
public class SoftReferenceUtils {

    private static Map<String, SoftReference> mSystemServiceMap = new HashMap<>();

    public synchronized static <T> T getSystemService(Context context, String serviceName){
        if(mSystemServiceMap.containsKey(serviceName)){
            SoftReference<T> softReference = mSystemServiceMap.get(serviceName);
            T systemService = softReference.get();
            if(systemService != null){
                return systemService;
            }
        }
        T systemService = (T) context.getSystemService(serviceName);
        mSystemServiceMap.put(serviceName, new SoftReference<>(systemService));
        return systemService;
    }

    public synchronized static <T> void set(String key, T t){
        mSystemServiceMap.put(key, new SoftReference<>(t));
    }

    public synchronized static <T> T get(String key){
        if(mSystemServiceMap.containsKey(key)){
            SoftReference<T> softReference = mSystemServiceMap.get(key);
            return softReference.get();
        }
        return null;
    }

    public synchronized static void clearSystemService(){
        if(mSystemServiceMap.isEmpty()){
            return;
        }
        mSystemServiceMap.clear();
    }

    public interface Key{
        String PACKAGE_MANAGER = "PackageManager";
    }

}
