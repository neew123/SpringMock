package com.mock.spring;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)  //生命周期
@Target(ElementType.TYPE)   //作用对象：类
public @interface EnableAspectJAutoProxy {

    String value() default "";  //
}
