package com.mock.service;

import com.mock.spring.*;

@Component
public class UserService implements BeanNameAware, InitializingBean {

    @Autowired
    private OrderService orderService;

    private String beanName;

    private String attribute;
    public void test(){
        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("不仅可以给对象里的属性赋值，还可以做其他事情");
    }
}
