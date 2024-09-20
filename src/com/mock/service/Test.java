package com.mock.service;

import com.mock.spring.MockApplicationContext;

public class Test {

    public static void main(String[] args) {
        //1.Spring容器，传入配置文件或配置类
        MockApplicationContext context = new MockApplicationContext(MockAppConfig.class);
        //2.获取容器里面的Bean
        UserService userService = (UserService) context.getBean("UserService");
    }
}
