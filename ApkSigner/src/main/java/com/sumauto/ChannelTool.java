package com.sumauto;

import java.util.Arrays;

public class ChannelTool {
    public static void main(String[] args) {
        System.out.println("main-->"+Arrays.toString(args));

        if (args.length == 0) {
            System.out.println("eg:");
            System.out.println("chtool -r abc.apk");
            System.out.println("chtool -w abc.apk bytedance");
            return;
        }
        if ("-r".equals(args[0])) {
            System.out.println(ApkChannel.getChannel(args[1]));
            return;
        }

        if ("-w".equals(args[0])) {
            String apkPath = args[1];
            String[] channels = args[2].split(",");
            for (String channel : channels) {
                new ApkChannel(apkPath,channel).run();
            }
            return;
        }
    }
}
