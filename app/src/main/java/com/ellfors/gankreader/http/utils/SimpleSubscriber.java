package com.ellfors.gankreader.http.utils;

import rx.Subscriber;

/**
 * 简单的Subscriber
 */
public abstract class SimpleSubscriber<T> extends Subscriber<T>
{
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }
}
