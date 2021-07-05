package com.com.sumauto.design.single;

public class InnerClassInstance {

    public static InnerClassInstance get() {
        return Holder.instance;
    }

    private InnerClassInstance() {

    }

    public void say() {
        System.out.println(getClass().getSimpleName());
    }

    private static class Holder {
        private static final InnerClassInstance instance = new InnerClassInstance();
    }
}
