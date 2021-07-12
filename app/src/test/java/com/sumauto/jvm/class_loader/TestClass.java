package com.sumauto.jvm.class_loader;

public class TestClass extends TestParent implements Runnable {

    public static final String TAG = "dddd";
    public static final int FINAL_V = 0;
    public static int SL_V = 0;

    static {
        System.out.println("TestClass clinit "+Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("TestClass clinit end "+Thread.currentThread().getName());


    }

    @Override
    public void run() {
        String ak = "ak";
    }
}
