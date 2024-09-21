package com.mock.service;

import com.mock.spring.BeanPostProcessor;
import com.mock.spring.Component;

@Component
public class ControlBeanPostProcessor implements BeanPostProcessor {

    @Override
    public void postProcessBeforeInitialization(String beanName, Object bean) {
        if(beanName.equals("userService")){
            System.out.println("userService BeforeInitialization");
          //控制userService对象
        }
    }

    @Override
    public void postProcessAfterInitialization(String beanName, Object bean) {
        if(beanName.equals("userService")){
            System.out.println("userService AfterInitialization");
            //控制userService对象
        }
    }
}
