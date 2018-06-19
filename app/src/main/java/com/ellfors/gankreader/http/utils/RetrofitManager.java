package com.ellfors.gankreader.http.utils;

import android.util.Log;

import com.ellfors.gankreader.http.config.HttpApi;
import com.ellfors.gankreader.http.config.RetrofitConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit操纵类
 */
public class RetrofitManager
{
    private static volatile RetrofitManager instance;
    private static String BASE_URL = RetrofitConfig.BASE_URL;
    private static int DEFAULT_TIME = RetrofitConfig.OUTTIME;

    private HttpLoggingInterceptor loggingInterceptor;

    public RetrofitManager()
    {
        loggingInterceptor = new HttpLoggingInterceptor(message -> Log.d(RetrofitConfig.HTTP_TAG, message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /**
     * 单例构造
     */
    public static RetrofitManager getInstance()
    {
        if (instance == null)
        {
            synchronized (RetrofitManager.class)
            {
                if (instance == null)
                {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取Gson解析的HttpApi
     */
    public HttpApi getGsonHttpApi()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME, java.util.concurrent.TimeUnit.SECONDS);
        builder.addInterceptor(loggingInterceptor);
        return new Retrofit
                .Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(HttpApi.class);
    }

    /**
     * 获取原始String的HttpApi
     */
    public HttpApi getStringHttpApi()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME, java.util.concurrent.TimeUnit.SECONDS);
        return new Retrofit
                .Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(HttpApi.class);
    }

}
