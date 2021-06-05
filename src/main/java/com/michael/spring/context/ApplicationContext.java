package com.michael.spring.context;

import com.michael.spring.annotation.Component;
import com.michael.spring.annotation.ComponentScan;
import com.michael.spring.annotation.Scope;
import com.michael.spring.bean.BeanDefination;
import com.michael.spring.exception.NoSuchBeanException;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Package: com.michael.spring.annotation
 * @ClassName: ApplicationContext
 * @Author: michael
 * @Date: 8:11
 * @Description:
 */
public class ApplicationContext {

    private Class configClass;
    private ConcurrentHashMap<String, Object> singleBeanMaps = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BeanDefination> beanDefinations = new ConcurrentHashMap<>();
    private Set<Class> classes = new HashSet<>();

    public ApplicationContext(Class configClass) throws Exception {
        this.configClass = configClass;
        scan(configClass);
    }

    private void scan(Class configClass) throws ClassNotFoundException {
        ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
        String path = componentScan.value();

        URL resource = configClass.getClassLoader().getResource(path.replace(".", "/"));
        scanBeanClass(resource);
        initialize();
    }

    private void initialize() {
        if (beanDefinations.size() > 0) {
            Enumeration<String> keys = beanDefinations.keys();
            while (keys.hasMoreElements()) {
                String beanName = keys.nextElement();
                BeanDefination beanDefination = beanDefinations.get(beanName);
                if (beanDefination.getScope().equals("single")) {
                    Object bean = createBean(beanDefination);
                    singleBeanMaps.put(beanName, bean);
                }
            }
        }
    }

    private Object createBean(BeanDefination beanDefination) {
        Class clazz = beanDefination.getClazz();
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName) throws NoSuchBeanException {
        if (singleBeanMaps.containsKey(beanName)) {
            return singleBeanMaps.get(beanName);
        } else if (beanDefinations.containsKey(beanName)) {
            return createBean(beanDefinations.get(beanName));
        } else {
            throw new NoSuchBeanException("no such bean exists");
        }
    }

    public void scanBeanClass(URL path) throws ClassNotFoundException {
        Set<File> resource = getMatchResource(path.getFile());
        for (File file : resource) {
            String filePath = file.getAbsolutePath();
            String classPath = filePath.substring(filePath.indexOf("com"), filePath.indexOf(".class"));
            classPath = classPath.replace("\\", ".");
            Class<?> clazz = this.configClass.getClassLoader().loadClass(classPath);
            if (clazz.isAnnotationPresent(Component.class)) {
                BeanDefination beanDefination = new BeanDefination(clazz);
                Scope scope = clazz.getDeclaredAnnotation(Scope.class);
                if (scope != null && scope.value() != null) {
                    beanDefination.setScope(scope.value());
                }
                String beanName = getBeanName(clazz);
                classes.add(clazz);
                beanDefinations.put(beanName, beanDefination);
            }
        }
    }

    private String getBeanName(Class<?> clazz) {
        Component component = clazz.getDeclaredAnnotation(Component.class);
        String name = component.name();
        if (name == null || "".equals(name)) {
            String clazzName = clazz.getSimpleName();
            String firstChar = clazzName.substring(0, 1);
            name = firstChar.toLowerCase() + clazzName.substring(1, clazzName.length());
        }
        return name;
    }

    public Set<File> getMatchResource(String path) {
        Set<File> resource = new HashSet<>();
        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                resource.add(file);
            } else {
                getMatchResources(file, resource);
            }
        }
        return resource;
    }

    public void getMatchResources(File file, Set<File> result) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                result.add(f);
            } else {
                getMatchResources(f, result);
            }
        }
    }
}
