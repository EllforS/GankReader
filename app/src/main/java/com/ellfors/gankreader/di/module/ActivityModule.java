package com.ellfors.gankreader.di.module;

import android.app.Activity;

import com.ellfors.gankreader.di.ActivityScope;
import com.ellfors.gankreader.ui.fragment.FuliFragment;
import com.ellfors.gankreader.ui.fragment.ReadFragment;
import com.ellfors.gankreader.ui.fragment.SettingFragment;
import com.ellfors.gankreader.ui.fragment.VideoFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule
{
    private Activity activity;

    public ActivityModule(Activity activity)
    {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity()
    {
        return activity;
    }

    @Provides
    @ActivityScope
    FuliFragment provideFuliFragment()
    {
        return new FuliFragment();
    }

    @Provides
    @ActivityScope
    ReadFragment provideReadFragment()
    {
        return new ReadFragment();
    }

    @Provides
    @ActivityScope
    VideoFragment provideVideoFragment()
    {
        return new VideoFragment();
    }

    @Provides
    @ActivityScope
    SettingFragment provideSettingFragment()
    {
        return new SettingFragment();
    }
}
