package com.com.sumauto.design.single;

public enum EnumInstance {
    INSTANCE;

    public void say() {
        System.out.println(getClass().getSimpleName());
    }

}
