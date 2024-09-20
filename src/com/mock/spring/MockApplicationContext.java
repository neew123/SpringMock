package com.mock.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class MockApplicationContext {

    private Class configClass;

    private ConcurrentHashMap<String,BeanDefinition> beanDefinitonMap = new ConcurrentHashMap<>();

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
    }

    //getBean方法
    public Object getBean(String beanName) {
        return null;
    }
}
