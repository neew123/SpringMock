package com.mock.service;

import com.mock.spring.Autowired;
import com.mock.spring.MockApplicationContext;

import java.lang.reflect.Field;

public class Test {

    public static void main(String[] args) {
        //1.Spring容器，传入配置文件或配置类
        MockApplicationContext context = new MockApplicationContext(MockAppConfig.class);
        //2.获取容器里面的Bean
        UserInterface userService = (UserInterface) context.getBean("userService");
        userService.test();
        //3.依赖注入
        //用无参的构造方法new一个对象
        UserService userService1 = new UserService();
        //属性赋值
        for(Field field:userService.getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(Autowired.class)){
                try {
                    field.setAccessible(true);
                    field.set(userService1,context.getBean(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        //测试容器里面单例bean对象的创建,userService的scope配置为@Scope("singleton")
//        System.out.println(context.getBean("userService"));
//        System.out.println(context.getBean("userService"));
//        测试容器里面多例bean对象的创建,userService的scope配置为@Scope("prototype")
//        System.out.println(context.getBean("userService"));
//        System.out.println(context.getBean("userService"));
    }
}
