package com.springmybatis;

import com.study.mapper.UserMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class StudyFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        Object proxyInstance = Proxy.newProxyInstance(StudyFactoryBean.class.getClassLoader(), new Class[]{UserMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName());
                return null;
            }
        });

        return proxyInstance;
    }

    @Override
    public Class<?> getObjectType() {
        return UserMapper.class;
    }
}
