package com.atguigu.spring.test;

import com.atguigu.spring.MyAnnotationConfiguration;
import com.atguigu.spring.entity.Employee;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author 杨鹏炜
 * @data 2021/4/8  19:46:09
 */
public class SpringTest {
    public static void main(String[] args) {
        // 以前使用new ClassPathXmlApplicationContext("");方式加载XML配置文件
        // 现在基于注解配置类创建IOC容器对象
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyAnnotationConfiguration.class);
        // 从IOC容器获取bean
        Employee employee = annotationConfigApplicationContext.getBean(Employee.class);
        System.out.println(employee);
        annotationConfigApplicationContext.close();
    }
}
