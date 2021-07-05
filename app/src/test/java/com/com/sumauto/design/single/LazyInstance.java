package com.com.sumauto.design.single;

/**
 * @deprecated 不安全，不推荐
 */
public class LazyInstance {

    private static LazyInstance instance;

    private LazyInstance() {

    }

    public void say() {
        System.out.println(getClass().getSimpleName());
    }


    public static LazyInstance get() {
        if (instance == null) {
            instance = new LazyInstance();
        }
        return instance;
    }


}
