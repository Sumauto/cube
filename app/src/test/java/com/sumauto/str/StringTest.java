package com.sumauto.str;

import org.junit.Test;

import java.util.regex.Matcher;

@SuppressWarnings("ALL")
public class StringTest extends Object implements Runnable {

    @Test
    public void testStack(){
        Thread.currentThread().dumpStack();
        ClassLoader loader;
    }

    @Test
    public void testConstantsPool2() {
        StringHolderA holderA = new StringHolderA();
        StringHolderB holderB = new StringHolderB();

        assert holderB.STATIC_FINAL_STR == holderB.STATIC_STR;
        assert holderB.STATIC_FINAL_STR == holderB.STR;
        assert holderB.OVER_CLASS_STR == holderA.OVER_CLASS_STR;
    }

    @SuppressWarnings("StringEquality")
    @Test
    public void testConstantsPool() {
        strEqual1();
        strEqual2();
        strEqual3();
        strEqual4();
        strEqual5();
    }

    private void strEqual1() {
        String s1 = "abc";
        String s2 = "abc";
        assert s1 == s2;
    }

    private void strEqual2() {
        String s1 = new String("abc");
        String s2 = new String("abc");
        assert s1 != s2;
    }

    private void strEqual3() {
        String s1 = "abc";
        String s2 = "a";
        String s3 = "bc";
        String s4 = s2 + s3;
        assert (s1 != s4);
    }

    private void strEqual4() {
        String s1 = "abc";
        final String s2 = "a";
        final String s3 = "bc";
        String s4 = s2 + s3;
        assert (s1 == s4);
    }

    private void strEqual5() {
        String s = new String("abc");
        String s1 = "abc";
        String s2 = new String("abc");
        System.out.println(s1 == s1.intern());
        System.out.println(s == s1.intern());
        System.out.println(s == s2.intern());
        System.out.println(s1 == s2.intern());
    }


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

    @Override
    public void run() {

    }
}
