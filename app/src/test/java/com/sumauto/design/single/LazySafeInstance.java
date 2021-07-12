package com.sumauto.design.single;

/**
 * @deprecated 频繁加锁，效率低，不推荐
 */
public class LazySafeInstance {

    private static LazySafeInstance instance;

    private LazySafeInstance(){

    }

    public void say() {
        System.out.println(getClass().getSimpleName());
    }


    public static synchronized LazySafeInstance get(){
        if (instance==null){
            instance=new LazySafeInstance();
        }
        return instance;
    }


}
