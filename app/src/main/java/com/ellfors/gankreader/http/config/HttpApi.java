package com.ellfors.gankreader.http.config;


import com.ellfors.gankreader.http.model.BaseCallModel;
import com.ellfors.gankreader.model.FuliModel;
import com.ellfors.gankreader.model.VideoModel;

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

    @GET("/api/data/休息视频/{limit}/{page}")
    Observable<BaseCallModel<List<VideoModel>>> getVideoList(
            @Path("limit") int limit,
            @Path("page") int page
    );
}
