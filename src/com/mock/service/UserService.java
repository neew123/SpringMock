package com.mock.service;

import com.mock.spring.Autowired;
import com.mock.spring.BeanNameAware;
import com.mock.spring.Component;
import com.mock.spring.Scope;

@Component
public class UserService implements BeanNameAware {

    @Autowired
    private OrderService orderService;

    private String beanName;

    public void test(){
        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = beanName;
    }
}
