package com.sumauto.design.factory;

import com.sumauto.design.factory.abs_factory.AbsFactory;
import com.sumauto.design.factory.abs_factory.BeijingFactory;
import com.sumauto.design.factory.abs_factory.NanjingFactory;
import com.sumauto.design.factory.method.AbsFruitFactory;
import com.sumauto.design.factory.method.AppleFactory;
import com.sumauto.design.factory.method.OrangeFactory;
import com.sumauto.design.factory.simple.SimpleFruitFactory;

import org.junit.Test;

/**
 * 简化复杂对象的创建过程，并且不修改对象的源码
 */
public class Demo {

    @Test
    public void testSimpleFactory(){
        SimpleFruitFactory factory=new SimpleFruitFactory();
        System.out.println(factory.create(SimpleFruitFactory.APPLE).getName());
        System.out.println(factory.create(SimpleFruitFactory.ORANGE).getName());
        System.out.println(factory.create("xxx"));
    }

    @Test
    public void testFactoryMethod(){
        AbsFruitFactory appleFactory=new AppleFactory();
        AbsFruitFactory orangeFactory=new OrangeFactory();
        System.out.println(appleFactory.create().getName());
        System.out.println(orangeFactory.create().getName());
    }
    @Test
    public void testAbsFactory(){
        AbsFactory factory=new BeijingFactory();
        System.out.println(factory.createApple().getName());
        System.out.println(factory.createOrange().getName());

        factory=new NanjingFactory();
        System.out.println(factory.createApple().getName());
        System.out.println(factory.createOrange().getName());

    }

}

