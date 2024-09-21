package com.mock.spring;


/**
 *  可以批量或单独处理某个Bean
 */
public interface BeanPostProcessor {

    public void postProcessBeforeInitialization(String beanName, Object bean);
    public void postProcessAfterInitialization(String beanName, Object bean);
}
