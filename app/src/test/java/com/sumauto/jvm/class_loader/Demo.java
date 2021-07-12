package com.sumauto.jvm.class_loader;

import org.junit.Test;

public class Demo {

    @Test
    public void testInit() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                runInit();
            }
        }, "thread-B").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                runInit();
            }
        }, "thread-C").start();


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runInit() {
        String prefix = Thread.currentThread().getName();

        System.out.println(prefix + " init start-->");
        System.out.println(prefix + " init get static final-->");
        //访问常量，类不会初始化
        int a = TestClass.FINAL_V;
        System.out.println(prefix+" init get static-->");

        //访问静态变量，类会初始化，并且优先初始化父类,类初始化会自动加锁
        //int b = TestClass.SL_V;

        try {
            //通过反射访问类，会触发初始化
            System.out.println(prefix+" reflect-->");
            Class.forName(TestClass.class.getName());
        } catch (ClassNotFoundException e) {
            System.out.println(prefix+" reflect error-->");
            e.printStackTrace();
        }
        System.out.println(prefix+" reflect end-->");



        System.out.println(prefix+" init end-->");


        //interface中不能有静态语句块，但仍可以有变量初始化的赋值操作，也可以生成clinit方法。
        // 但接口和类的不同是，执行接口的clinit方法不需要先执行父接口的clinit方法。
        // 只有当父接口中定义的变量使用时，父接口才会初始化。
    }
}
