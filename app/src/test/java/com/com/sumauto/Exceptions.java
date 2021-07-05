package com.com.sumauto;

import org.junit.Test;

import java.util.regex.Matcher;

public class Exceptions {
    @Test
    public void strRepaceException() {
        String str = "abcd_?";
        try {
            System.out.println(str.replaceAll("\\?", "$"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(str.replaceFirst("\\?", "$"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(str.replace("?", "$"));
        System.out.println(str.replaceAll("\\?", Matcher.quoteReplacement("$")));

    }
}
