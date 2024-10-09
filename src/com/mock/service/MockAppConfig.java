package com.mock.service;


import com.mock.spring.ComponentScan;
import com.mock.spring.EnableAspectJAutoProxy;

@ComponentScan("com.mock")
@EnableAspectJAutoProxy  //打开AOP开关
public class MockAppConfig {
    //1.通过注解的方式配置扫描路径
}
