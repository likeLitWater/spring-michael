package com.michael.test.service;

import com.michael.spring.annotation.Component;
import com.michael.spring.annotation.Scope;

/**
 * @Package: com.michael.test.service
 * @ClassName: UserService
 * @Author: michael
 * @Date: 8:14
 * @Description:
 */
@Component
@Scope("prototype")
public class UserService {

    public void addUser() {
        System.out.println("add zhangsan....");
    }
}
