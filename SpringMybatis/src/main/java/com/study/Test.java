package com.study;

import com.study.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.study");
        UserService userService = (UserService) context.getBean("userService");
        userService.test();
    }
}
