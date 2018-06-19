package com.ellfors.gankreader.http.utils;

import com.ellfors.gankreader.http.model.BaseCallModel;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程切换、统一封装回调处理
 */
public class RxUtils
{
    /**
     * 统一线程处理
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper()
    {
        //compose简化线程
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 统一返回结果处理
     */
    public static <T> FlowableTransformer<BaseCallModel<T>, T> handleResult()
    {
        //compose判断结果
        return upstream -> upstream.flatMap((Function<BaseCallModel<T>, Publisher<T>>) model ->
        {
            if (!model.isError())
            {
                return createData(model.getResults());
            }
            else
            {
                /*
                 * 可以根据不同的Error_code返回不同的Exception
                 * 比如token过期，需要重新登录
                 */
                return Flowable.error(new Exception("这里是自定义Error"));
            }
        });
    }

    /**
     * 生成Observable
     */
    private static <T> Flowable<T> createData(final T t)
    {
        return Flowable.create(emitter ->
        {
            try
            {
                if (t != null)
                    emitter.onNext(t);
                emitter.onComplete();
            }
            catch (Exception e)
            {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
    }
}
