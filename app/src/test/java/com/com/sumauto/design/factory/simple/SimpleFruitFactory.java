package com.com.sumauto.design.factory.simple;

import com.com.sumauto.design.factory.items.Apple;
import com.com.sumauto.design.factory.AbsFruit;
import com.com.sumauto.design.factory.items.Orange;

/**
 *
 */
public class SimpleFruitFactory {

    public static final String APPLE = "apple";
    public static final String ORANGE = "orange";

    public AbsFruit create(String fruitName) {
        if (APPLE.equals(fruitName)) {
            return new Apple();
        }

        if (ORANGE.equals(fruitName)) {
            return new Orange();
        }

        return null;
    }

}
