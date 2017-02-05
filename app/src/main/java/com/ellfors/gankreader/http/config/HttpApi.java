package com.ellfors.gankreader.http.config;


import com.ellfors.gankreader.http.model.BaseCallModel;
import com.ellfors.gankreader.model.AndroidModel;
import com.ellfors.gankreader.model.FuliModel;
import com.ellfors.gankreader.model.IOSModel;
import com.ellfors.gankreader.model.WebModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Retrofit 构造请求Service
 */
public interface HttpApi
{
    @GET("/api/data/福利/{limit}/{page}")
    Observable<BaseCallModel<List<FuliModel>>> getFuLi(
            @Path("limit") int limit,
            @Path("page") int page
    );

    @GET("/api/data/Android/{limit}/{page}")
    Observable<BaseCallModel<List<AndroidModel>>> getAndroidList(
            @Path("limit") int limit,
            @Path("page") int page
    );

    @GET("/api/data/iOS/{limit}/{page}")
    Observable<BaseCallModel<List<IOSModel>>> getIOSList(
            @Path("limit") int limit,
            @Path("page") int page
    );

    @GET("/api/data/前端/{limit}/{page}")
    Observable<BaseCallModel<List<WebModel>>> getWebList(
            @Path("limit") int limit,
            @Path("page") int page
    );

    @GET("/api/random/data/福利/{num}")
    Observable<BaseCallModel<List<FuliModel>>> getRandomFuli(
            @Path("num") int num
    );
}
