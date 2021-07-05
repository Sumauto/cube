package com.com.sumauto.design.single;

public class DoubleCheckLazyInstance {

    private static DoubleCheckLazyInstance instance;

    private DoubleCheckLazyInstance(){

    }

    public void say() {
        System.out.println(getClass().getSimpleName());
    }


    public static DoubleCheckLazyInstance get(){
        if (instance==null){
            synchronized (DoubleCheckLazyInstance.class){
                if (instance==null){
                    instance=new DoubleCheckLazyInstance();
                }
            }
        }
        return instance;
    }


}
