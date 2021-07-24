package com.sumauto;

import java.io.File;
import java.io.FileNotFoundException;
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
            writeChannel(apkPath,channels);
        }
    }


    public static void writeChannel(String pathArg,String[] channels) {
        //添加任务
        //"ApkSigner/apk/app-debug.apk"
        File file = new File(pathArg);
        if (!file.exists()) {
            throw new RuntimeException(new FileNotFoundException(pathArg));
        }
        String absPath = file.getAbsolutePath();

        for (String channel : channels) {

            writeChannel(absPath, channel);
        }

    }

    private static void writeChannel(String path, String channel) {
        String value = "{\"hume_channel_id\": \"" + channel + "\"}";
        new ApkChannel(path, ApkChannel.HUME_CHANNEL_ID, value, channel).run();

    }
}
