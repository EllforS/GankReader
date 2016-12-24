package com.ellfors.gankreader.di.module;

import android.app.Activity;

import com.ellfors.gankreader.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule
{
    private Activity activity;

    public FragmentModule(Activity activity)
    {
        this.activity = activity;
    }

    @Provides
    @FragmentScope
    Activity provideActivity()
    {
        return activity;
    }
}
