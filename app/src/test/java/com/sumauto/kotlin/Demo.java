package com.sumauto.kotlin;

import org.junit.Test;

public class Demo {

    @Test
    public void testDemo() {
        new Item("").setDns("");
        new Item("").dns();
    }

    @Test
    public void tableSizeFor() {

        int n = 1;
        System.out.println(n+" "+Integer.toBinaryString(n));
        n |= n >>> 1;
        System.out.println(n+" "+Integer.toBinaryString(n));
        n |= n >>> 2;
        System.out.println(n+" "+Integer.toBinaryString(n));
        n |= n >>> 4;
        System.out.println(n+" "+Integer.toBinaryString(n));
        n |= n >>> 8;
        System.out.println(n+" "+Integer.toBinaryString(n));
        n |= n >>> 16;
        System.out.println(n+" "+Integer.toBinaryString(n));
    }
}
