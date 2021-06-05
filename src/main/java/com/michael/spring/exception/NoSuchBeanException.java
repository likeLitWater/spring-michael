package com.michael.spring.exception;

/**
 * @Package: com.michael.spring.exception
 * @ClassName: NoNuchBeanException
 * @Author: michael
 * @Date: 17:18
 * @Description:
 */
public class NoSuchBeanException extends Throwable {
    public NoSuchBeanException(String message) {
        super(message);
    }
}
