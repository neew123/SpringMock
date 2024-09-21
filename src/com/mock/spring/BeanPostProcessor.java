package com.mock.spring;


/**
 *  可以批量或单独处理某个Bean
 */
public interface BeanPostProcessor {

    public Object postProcessBeforeInitialization(String beanName, Object bean);
    public Object postProcessAfterInitialization(String beanName, Object bean);
}
