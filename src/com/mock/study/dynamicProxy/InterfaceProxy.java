package com.mock.study.dynamicProxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理：基于接口的动态代理
 * （1）要求被代理的对象必须实现一个或多个接口
 */

interface MyInterface{
    void doSomething();
}


class MyRealObject implements MyInterface{
    public void doSomething() {
        System.out.println("doSomething");
    }
}

//第一种实现方式
public class InterfaceProxy implements InvocationHandler {
    private final Object object;

    public InterfaceProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before method call.");
        Object result = method.invoke(object, args);
        return result;
    }
}

class DynamicProxyTest {
    public static void main(String[] args) {
        MyRealObject myRealObject = new MyRealObject();
        //第二种实现方式
        MyInterface proxyInstance = (MyInterface)Proxy.newProxyInstance(MyInterface.class.getClassLoader(), new Class[]{MyInterface.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before method call.");
                Object result = method.invoke(myRealObject, args);
                return result;
            }
        });
        proxyInstance.doSomething();


        // 获取当前类的类加载器
        ClassLoader myRealObjectClassLoader = MyRealObject.class.getClassLoader();
        // 输出类加载器的信息
        System.out.println("MyInterface class loader: " + MyInterface.class.getClassLoader());
        System.out.println("MyRealObject class loader: " + myRealObjectClassLoader);
        System.out.println("InterfaceProxy class loader: " + InterfaceProxy.class.getClassLoader());
        // 获取系统的应用类加载器
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("System class loader: " + appClassLoader);
        // 获取扩展类加载器
        ClassLoader extClassLoader = appClassLoader.getParent();
        System.out.println("Extension class loader: " + extClassLoader);
        // 获取引导类加载器，由于Bootstrap ClassLoader没有父类加载器，所以返回null
        ClassLoader bootClassLoader = extClassLoader.getParent();
        System.out.println("Bootstrap class loader: " + bootClassLoader);

    }
}
