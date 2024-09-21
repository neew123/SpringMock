package com.mock.service;

import com.mock.spring.BeanPostProcessor;
import com.mock.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class ControlBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if(beanName.equals("userService")){
            System.out.println("userService BeforeInitialization");
          //控制userService对象
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        if(beanName.equals("userService")){
            System.out.println("userService AfterInitialization");
            Object proxyInstance = Proxy.newProxyInstance(ControlBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("切面逻辑");
                    return method.invoke(bean, args); //执行代理对象原来的方法
                }
            });

            return proxyInstance;
        }

        return bean;
    }
}
