package com.sumauto;

public class ApkChannelWriter {
    public static void main(String[] args){
        System.out.println(ApkChannel.getChannel("./ApkSigner/apk/app-bytedance.apk"));
        System.out.println(ApkChannel.getChannel("./ApkSigner/apk/app-bytedance2.apk"));
    }


}
