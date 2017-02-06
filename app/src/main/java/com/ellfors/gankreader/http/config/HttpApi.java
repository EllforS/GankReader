package com.ellfors.gankreader.http.config;


import com.ellfors.gankreader.http.model.BaseCallModel;
import com.ellfors.gankreader.model.RandomModel;
import com.ellfors.gankreader.model.StudyModel;
import com.ellfors.gankreader.model.FuliModel;

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

    @GET("/api/data/{type}/{limit}/{page}")
    Observable<BaseCallModel<List<StudyModel>>> getStudyList(
            @Path("type") String type,
            @Path("limit") int limit,
            @Path("page") int page
    );

    @GET("/api/random/data/福利/{num}")
    Observable<BaseCallModel<List<RandomModel>>> getRandomFuli(
            @Path("num") int num
    );
}
