package com.mock.service;

import com.mock.spring.*;

@Component
public class UserService implements BeanNameAware, InitializingBean, UserInterface {

    @Autowired
    private OrderService orderService;

    @Autowired
    private User admin;

    private String beanName;

    private String attribute;


    @PostConstruct
    public void iniAdmin(){
        //在UserService初始化前，这里获取admin对象需要从数据库获取。
    }
    public void test(){
        System.out.println("userService test");;
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
