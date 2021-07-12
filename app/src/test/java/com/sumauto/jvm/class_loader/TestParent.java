package com.sumauto.jvm.class_loader;

public class TestParent {
    public static final int FINAL_V=0;
    public static  int SL_V=0;
    static {
        System.out.println("TestParent clinit");
    }
}
