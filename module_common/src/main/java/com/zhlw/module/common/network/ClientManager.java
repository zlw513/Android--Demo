package com.zhlw.module.common.network;

import android.content.Context;
import android.util.Log;
import com.zhlw.module.common.BuildConfig;
import java.io.File;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ClientManager {

    public static final String LOG_TAG = ClientManager.class.getSimpleName();

    private static final int DEFAULT_TIMEOUT = 5;

    private ClientManager(){

    }

    public static OkHttpClient getOkHttpClient(Context context){
        return configurationOkHttpClient(context);
    }

    public static Retrofit getRetrofitClient(String baseUrl,OkHttpClient okHttpClient){
        Log.d(LOG_TAG, "init baseUrl = " + baseUrl);
        return new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
    }

    /**
     * 配置 OkHttpClient
     */
    private static OkHttpClient configurationOkHttpClient(Context context) {
        if (null == context) {
            return null;
        }
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), "tamic_cache"), 10 * 1024 * 1024);
        } catch (Exception e) {
            Log.d(LOG_TAG, "configurationOkHttpClient error:" + e.getMessage());
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog","retrofitBack = "+message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                .retryOnConnectionFailure(false);
        if (!BuildConfig.DEBUG) {
            //设置不能代理抓包
            builder.proxy(Proxy.NO_PROXY);
        }
        return builder.build();
    }

}
