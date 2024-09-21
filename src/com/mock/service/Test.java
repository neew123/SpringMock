package com.mock.service;

import com.mock.spring.MockApplicationContext;

public class Test {

    public static void main(String[] args) {
        //1.Spring容器，传入配置文件或配置类
        MockApplicationContext context = new MockApplicationContext(MockAppConfig.class);
        //2.获取容器里面的Bean
        UserInterface userService = (UserInterface) context.getBean("userService");
        userService.test();

        //测试容器里面单例bean对象的创建,userService的scope配置为@Scope("singleton")
//        System.out.println(context.getBean("userService"));
//        System.out.println(context.getBean("userService"));
//        测试容器里面多例bean对象的创建,userService的scope配置为@Scope("prototype")
//        System.out.println(context.getBean("userService"));
//        System.out.println(context.getBean("userService"));
    }
}
