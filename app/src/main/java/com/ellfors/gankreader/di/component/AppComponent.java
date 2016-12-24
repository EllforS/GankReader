package com.ellfors.gankreader.di.component;

import com.ellfors.gankreader.app.MyApplication;
import com.ellfors.gankreader.di.module.AppModule;
import com.ellfors.gankreader.http.utils.RetrofitManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent
{
    MyApplication myApplication();
    RetrofitManager retrofitManager();

    void inject(MyApplication myApplication);
}
