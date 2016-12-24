package com.ellfors.gankreader.di.module;


import com.ellfors.gankreader.app.MyApplication;
import com.ellfors.gankreader.http.utils.RetrofitManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule
{
    private MyApplication myApplication;

    public AppModule(MyApplication myApplication)
    {
        this.myApplication = myApplication;
    }

    @Provides
    @Singleton
    MyApplication provideMyApplication()
    {
        return myApplication;
    }

    @Provides
    @Singleton
    RetrofitManager provideRetrofitManager()
    {
        return RetrofitManager.getInstance();
    }
}
