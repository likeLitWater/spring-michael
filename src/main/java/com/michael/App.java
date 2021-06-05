package com.michael;

import com.michael.spring.context.ApplicationContext;
import com.michael.spring.config.MainConfig;
import com.michael.spring.exception.NoSuchBeanException;

/**
 * @Package: com.michael
 * @ClassName: App
 * @Author: michael
 * @Date: 8:16
 * @Description:
 */
public class App {
    public static void main(String[] args) throws Exception, NoSuchBeanException {
        ApplicationContext applicationContext = new ApplicationContext(MainConfig.class);
        System.out.println(applicationContext.getBean("userService"));
        System.out.println(applicationContext.getBean("userService"));
        System.out.println(applicationContext.getBean("userService"));
    }
}
