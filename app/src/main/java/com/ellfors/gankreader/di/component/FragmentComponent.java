package com.ellfors.gankreader.di.component;

import android.app.Activity;

import com.ellfors.gankreader.di.FragmentScope;
import com.ellfors.gankreader.di.module.FragmentModule;
import com.ellfors.gankreader.ui.fragment.FuliFragment;
import com.ellfors.gankreader.ui.fragment.MainFragment;
import com.ellfors.gankreader.ui.fragment.StudyFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent
{
    Activity activity();

    void inject(MainFragment mainFragment);
    void inject(StudyFragment studyFragment);
    void inject(FuliFragment fuliFragment);
}
