package com.mock.spring;

public class BeanDefinition {

    private Class type; //Bean的类型
    private String scope;//单例还是多例


    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
