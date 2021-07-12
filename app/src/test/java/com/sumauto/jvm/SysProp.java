package com.sumauto.jvm;

import org.junit.Test;

public class SysProp {

    @Test
    public void testProps(){
        System.out.println(System.getProperty("java.vm.version"));
    }
}
