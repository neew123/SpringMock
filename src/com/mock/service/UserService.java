package com.mock.service;

import com.mock.spring.Autowired;
import com.mock.spring.Component;
import com.mock.spring.Scope;

@Component
public class UserService {

    @Autowired
    private OrderService orderService;

    public void test(){
        System.out.println(orderService);
    }
}
