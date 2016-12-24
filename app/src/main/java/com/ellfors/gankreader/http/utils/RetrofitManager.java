package com.ellfors.gankreader.http.utils;

import com.ellfors.gankreader.http.config.HttpApi;
import com.ellfors.gankreader.http.config.RetrofitConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit操纵类
 */
public class RetrofitManager {

    private static volatile RetrofitManager instance;
    public static String BASE_URL;
    public static int DEFAULT_TIME;

    private static HttpApi httpApi;

    public RetrofitManager()
    {
        BASE_URL = RetrofitConfig.BASE_URL;
        DEFAULT_TIME = RetrofitConfig.OUTTIME;
    }

    /**
     * 单例构造
     */
    public static RetrofitManager getInstance()
    {
        if(instance == null)
        {
            synchronized (RetrofitManager.class)
            {
                if(instance == null)
                {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取Gson解析的HttpApi
     *
     * @return httpApi
     */
    public HttpApi getGsonHttpApi() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME, java.util.concurrent.TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit
                .Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        httpApi = retrofit.create(HttpApi.class);

        return httpApi;
    }

    /**
     * 获取原始String的HttpApi
     *
     * @return httpApi
     */
    public HttpApi getStringHttpApi() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME, java.util.concurrent.TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit
                .Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        httpApi = retrofit.create(HttpApi.class);

        return httpApi;
    }

}
