package com.mock.service;

import com.mock.spring.Autowired;
import com.mock.spring.MockApplicationContext;
import com.mock.spring.PostConstruct;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * UserService.class --> 无参构造方法(推断构造方法) --> 普通对象 --> 依赖注入 --> 初始化前(@PostConstruct) ---> 初始化(afterPropertiesSet) --->
 * 初始化后(AOP) ---> 代理对象(没有对代理对象属性进行依赖注入，代理对象加入target属性来解决)---> 放入Map --> Bean对象
 *
 * （1）推断构造方法：如果有多个构造方法，Spring会去找默认无参构造方法，如果没有，则抛出异常（解决办法：在相应的构造方法上加上@Autowired注解）；
 * 如果只有一个构造方法，Spring就会用那一个。
 *    如构造方法有参数：public UserService(OrderService orderService){}; 这时Spring会首先从单例池里面找OrderService对象，如果没有，则new一个OrderService对象.
 * 这时可能会出现循环依赖。找OrderService对象的逻辑：先根据类型ByType查找，如果没有，则根据名称ByName查找。
 *
 * （2）依赖注入方式：同（1）中的先根据类型ByType查找，如果没有，则根据名称ByName查找。
 *
 * （3）代理对象：UserServiceProxy对象 --> UserService代理对象  --> UserService代理对象的target属性赋值为UserService对象
 *
 * */

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
        //方法在bean对象初始化前调用
        for(Method method:userService1.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(PostConstruct.class)){
                try {
                    method.invoke(userService1,null);
                } catch (Exception e) {
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
