package com.mock.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class MockApplicationContext {

    private Class configClass;

    private ConcurrentHashMap<String,BeanDefinition> beanDefinitonMap = new ConcurrentHashMap<>();

    //单例池
    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();

    public MockApplicationContext(Class configClass) {
        this.configClass = configClass;
        //扫描：1.获取扫描路径
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            //获取当前扫描注解的属性值
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScanAnnotation.value(); //  扫描路径：com.mock.service,但是要获取编译后的.class文件夹作为扫描路径
            path = path.replace(".", "/"); //   扫描路径：com/mock/service

            //获取当前容器的classLoader
            ClassLoader classLoader = MockApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);//从类加载器里面获取相对路径path所对应的资源

            File file = new File(resource.getFile()); // /Users/yuanmengli/IdeaProjects/SpringMock/out/production/SpringMock/com/mock/service
            //判断当前是否为文件夹
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                //筛选class文件
                for (File f : files) {
                    String absolutePath = f.getAbsolutePath();  //  /Users/yuanmengli/IdeaProjects/SpringMock/out/production/SpringMock/com/mock/service/UserService.class
                    if (absolutePath.endsWith(".class")) {//判断是否是bean，即判断某类是否有Component注解
                        //路径截取
                        String className = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class")); // com/mock/service/UserService
                        //路径替换
                        className = className.replace("/", ".");//  com.mock.service.UserService
                        //使用类加载器根据名称获取该类
                        try {
                            Class<?> aClass = classLoader.loadClass(className);
                            //判断这个类是否存在Component注解
                            if (aClass.isAnnotationPresent(Component.class)) {
                                //获取bean的名字
                                Component component = aClass.getAnnotation(Component.class);
                                String beanName = component.value();
                                //如果Bean的名字为空，则需要默认生成Bean的名字:第一个字母小写
                                if(beanName.equals("")){
                                    beanName = Introspector.decapitalize(aClass.getSimpleName());
                                }
                                //Bean，Spring扫描的时候并不应该把对象创建出来，因为存在单例和多例bean。可以创建BeanDefinition对象
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(aClass);
                                if (aClass.isAnnotationPresent(Scope.class)) {
                                    Scope scope = aClass.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scope.value());
                                } else {
                                    beanDefinition.setScope("singleton");
                                }
                                // 将beanDefinition对象保存到map中
                                beanDefinitonMap.put(beanName,beanDefinition);

                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            }
        }

        //创建所有的单例对象
        for (String beanName : beanDefinitonMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitonMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")){
                Object bean = createBean(beanName,beanDefinition);
                singletonObjects.put(beanName,bean);
            }

        }
    }

    private Object createBean(String beanName,BeanDefinition beanDefinition){
        //获取当前Bean对应的类
        Class clazz = beanDefinition.getType();
        //反射获取构造方法来构建对象
        try {
            Object instance = clazz.getConstructor().newInstance();
            //依赖注入简单版实现：给加了Autowired注解的属性进行注入
            for (Field f : clazz.getDeclaredFields()) { //获取类所有声明的字段，包括受保护的和私有的
                if(f.isAnnotationPresent(Autowired.class)){
                    //绕开访问限制，访问受保护的和私有成员
                    f.setAccessible(true);
                    //给属性赋值，通过getBean方法从容器中找出这个Bean对象然后再赋值
                    f.set(instance,getBean(f.getName()));
                }
            }

            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        
    }


    //getBean方法
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitonMap.get(beanName);
        if(beanDefinition == null){
            throw new NullPointerException();
        }else {
            String scope = beanDefinition.getScope();//获取作用域
            if(scope.equals("singleton")){
                //单例：在Spring容器启动的时候就把所有的单例bean对象创建,保存在单例池，这样获取的时候直接从单例池获取
                Object bean = singletonObjects.get(beanName);
                if(bean == null){
                    bean = createBean(beanName,beanDefinition);
                    singletonObjects.put(beanName,bean);
                }
                return bean;
            }else {
                //多例
                return createBean(beanName,beanDefinition);
            }
        }
    }
}
