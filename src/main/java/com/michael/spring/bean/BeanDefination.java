package com.michael.spring.bean;

/**
 * @Package: com.michael.spring.bean
 * @ClassName: BeanDefination
 * @Author: michael
 * @Date: 16:46
 * @Description:
 */
public class BeanDefination {

    private Class clazz;
    private String scope = "single";

    public BeanDefination(Class clazz) {
        this.clazz = clazz;
    }

    public BeanDefination(Class clazz, String scope) {
        this.clazz = clazz;
        this.scope = scope;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
