package com.sumauto.design.single;

import org.junit.Test;

public class Demo {
    @Test
    public void run(){
        LazyInstance.get().say();
        LazySafeInstance.get().say();
        HungryInstance.get().say();
        DoubleCheckLazyInstance.get().say();
        InnerClassInstance.get().say();
        EnumInstance.INSTANCE.say();
        KotlinInstance.INSTANCE.say();
    }
}
