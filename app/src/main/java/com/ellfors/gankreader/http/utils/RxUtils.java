package com.ellfors.gankreader.http.utils;

import com.ellfors.gankreader.http.model.BaseCallModel;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 线程切换、统一封装回调处理
 */
public class RxUtils
{
    /**
     * 统一线程处理
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper()
    {
        //compose简化线程
        return tObservable ->
                tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 统一返回结果处理
     * 这里用于处理GankApi的BaseModel
     * 不同BaseUrl的请求BaseModel也不同
     */
    public static <T> Observable.Transformer<BaseCallModel<T>, T> handleResult()
    {
        //compose判断结果
        return baseCallModelObservable ->
                baseCallModelObservable.flatMap(new Func1<BaseCallModel<T>, Observable<T>>()
                {
                    @Override
                    public Observable<T> call(BaseCallModel<T> model)
                    {
                        if (!model.isError())
                        {
                            return createData(model.getResults());
                        }
                        else
                        {
                            /**
                             * 可以根据不同的Error_code返回不同的Exception
                             * 比如token过期，需要重新登录
                             */
                            return Observable.error(new Exception("这里是自定义Error"));
                        }
                    }
                });
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T t)
    {
        return Observable.create(new Observable.OnSubscribe<T>()
        {
            @Override
            public void call(Subscriber<? super T> subscriber)
            {
                try
                {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                }
                catch (Exception e)
                {
                    subscriber.onError(e);
                }
            }
        });
    }
}
