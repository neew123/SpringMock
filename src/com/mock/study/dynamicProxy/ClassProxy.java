//package com.mock.study.dynamicProxy;
//
///**
// * CGLIB动态代理：基于子类的动态代理
// * (1)通过字节码技术为一个类创建子类，并在子类中加入拦截功能
// *（2）对于没有实现接口的类可以采用此方式实现动态代理
// *
// */
//
//import net.sf.cglib.proxy.Enhancer;
//import net.sf.cglib.proxy.MethodInterceptor;
//import net.sf.cglib.proxy.MethodProxy;
//
//import java.lang.reflect.Method;
//
// class NoInterfaceSubject {
//    public void doSomething() {
//        System.out.println("No interface subject doing something.");
//    }
//}
//
//class CglibProxy implements MethodInterceptor {
//    private final Object target;
//
//    public CglibProxy(Object target) {
//        this.target = target;
//    }
//
//    @Override
//    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//        System.out.println("Before method call.");
//        Object result = proxy.invokeSuper(obj, args);
//        System.out.println("After method call.");
//        return result;
//    }
//}
//
//class CglibDynamicProxyDemo {
//    public static void main(String[] args) {
//        NoInterfaceSubject noInterfaceSubject = new NoInterfaceSubject();
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(noInterfaceSubject.getClass());
//        enhancer.setCallback(new CglibProxy(noInterfaceSubject));
//        NoInterfaceSubject proxyInstance = (NoInterfaceSubject) enhancer.create();
//        proxyInstance.doSomething();
//    }
//}
//
