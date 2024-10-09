package com.mock.aspect;

import com.mock.spring.Aspect;
import com.mock.spring.Before;
import com.mock.spring.Component;

@Aspect
@Component
public class mockAspect {
    @Before("execution(public void com.mock.service.UserService.test())")
    public void mockBefore(){
        System.out.println("mockBefore");
    }
}
