package com.ellfors.gankreader.di.component;

import android.app.Activity;

import com.ellfors.gankreader.di.ActivityScope;
import com.ellfors.gankreader.di.module.ActivityModule;
import com.ellfors.gankreader.ui.activity.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent
{
    Activity activity();

    void inject(MainActivity activity);
}
