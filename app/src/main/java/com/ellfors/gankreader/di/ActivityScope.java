package com.ellfors.gankreader.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Scope
@Retention(RUNTIME)
public @interface ActivityScope
{
    /**
     * 这个注解是自定义的，对应Activity的生命周期
     *
     * @Scope 自定义注解，限制注解作用域
     */
}
