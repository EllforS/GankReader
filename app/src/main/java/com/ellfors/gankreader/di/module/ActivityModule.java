package com.ellfors.gankreader.di.module;

import android.app.Activity;

import com.ellfors.gankreader.di.ActivityScope;
import com.ellfors.gankreader.ui.fragment.AboutFragment;
import com.ellfors.gankreader.ui.fragment.FuliFragment;
import com.ellfors.gankreader.ui.fragment.LikeFragment;
import com.ellfors.gankreader.ui.fragment.ReadFragment;
import com.ellfors.gankreader.ui.fragment.SettingFragment;

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
    LikeFragment provideLikeFragment()
    {
        return new LikeFragment();
    }

    @Provides
    @ActivityScope
    SettingFragment provideSettingFragment()
    {
        return new SettingFragment();
    }

    @Provides
    @ActivityScope
    AboutFragment provideAboutFragment()
    {
        return new AboutFragment();
    }
}
