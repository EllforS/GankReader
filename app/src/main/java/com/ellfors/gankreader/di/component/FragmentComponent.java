package com.ellfors.gankreader.di.component;

import android.app.Activity;

import com.ellfors.gankreader.di.FragmentScope;
import com.ellfors.gankreader.di.module.FragmentModule;
import com.ellfors.gankreader.ui.fragment.FuliFragment;
import com.ellfors.gankreader.ui.fragment.LikeFragment;
import com.ellfors.gankreader.ui.fragment.ReadFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent
{
    Activity activity();

    void Inject(ReadFragment readFragment);
    void inject(FuliFragment fuliFragment);
    void inject(LikeFragment likeFragment);
}
