package com.atguigu.spring;

import com.atguigu.spring.entity.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 杨鹏炜
 * @data 2021/4/8  19:41:32
 * 表示当前类是一个配置类，作用大致相当于以前的spring-context.xml这样的配置文件
 */
@Configuration
public class MyAnnotationConfiguration {
    /**
     * @Bean注解相当于做了下面XML标签的配置，把方法的返回值放入IOC容器
     * <bean id="emp" class="com.atguigu.spring.entity.Employee"/>
     * @return
     */
    @Bean
    public Employee getEmployee() {
        return new Employee();
    }
}
