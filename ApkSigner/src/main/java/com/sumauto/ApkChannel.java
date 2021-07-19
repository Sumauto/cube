package com.sumauto;

import com.android.apksig.apk.ApkSigningBlockNotFoundException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.apk.v2.V2SchemeSigner;
import com.android.apksig.internal.apk.v3.V3SchemeSigner;
import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSinks;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.DataSources;
import com.android.apksig.zip.ZipFormatException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ApkChannel {


    public static final int PADDING_BLOCK_ID = 0x42726577;
    public static final int CHANNEL_ID = 0x33333333;
    private final File inputFile;
    private final File outPutFile;
    private final String channel;


    public ApkChannel(String apkPath, String channel) {
        inputFile = new File(apkPath);
        String outPath;
        if (apkPath.endsWith(".apk")) {
            outPath = String.format("%s-%s.apk", apkPath.substring(0, apkPath.length() - 4), channel);
        } else {
            outPath = apkPath + ".apk";
        }
        outPutFile = new File(outPath);
        this.channel = channel;
    }


    public static void main(String[] arg) {
        System.out.println(new File(".").getAbsolutePath());
        //添加任务
        String path = new File("ApkSigner/apk/app-debug.apk").getAbsolutePath();
        new ApkChannel(path, "bytedance").run();
        new ApkChannel(path, "bytedance2").run();
    }

    public void run() {
        try {
            writeChannel(channel);

            //differBytes();

            System.out.println("dump new apk--->>");
            dumpSigningBlock(getSigningBlock(DataSources.asDataSource(new RandomAccessFile(outPutFile, "r"))).getContents(), null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * byte:EE (8 bit)
     * short:EE EE
     * int:EE EE EE EE
     * long:EE EE EE EE EE EE EE EE
     */
    private void writeChannel(String channel) throws IOException, ZipFormatException, ApkSigningBlockNotFoundException {

        DataSource inputApk = DataSources.asDataSource(new RandomAccessFile(inputFile, "r"));

        ApkUtils.ApkSigningBlock signingBlock = getSigningBlock(inputApk);
        DataSource signingBlockDataSource = signingBlock.getContents();


        System.out.println("dump raw apk--->> ");
        DumpInfo info = new DumpInfo();
        dumpSigningBlock(signingBlockDataSource, info);

        System.out.println("make copy--->>");
        //make a copy
        RandomAccessFile outputFile = new RandomAccessFile(outPutFile, "rw");
        outputFile.setLength(0L);

        DataSink outputApkOut = DataSinks.asDataSink(outputFile);

        inputApk.feed(0, inputApk.size(), outputApkOut);
        outputFile.close();

        System.out.println("write channel--->");

        //开始写渠道号
        outputFile = new RandomAccessFile(outPutFile, "rw");
        final long paddingBlockOffset = info.paddingBlockOffset;
        final long paddingBlockSize = info.paddingBlockSize;
        long pairSize = channel.getBytes().length + 4;

        long blockStartOffset = signingBlock.getStartOffset();
        long startOffset = blockStartOffset + paddingBlockOffset;

        System.out.println("blockStartOffset " + blockStartOffset + " ,paddingBlockOffset:" + paddingBlockOffset + " ,total=" + outputFile.length());

        outputFile.seek(startOffset);
        outputFile.write(littleBytes(pairSize));
        outputFile.write(littleBytes(CHANNEL_ID));
        outputFile.writeBytes(channel);

        //写入Padding Block -8-4-len  -len-4
        outputFile.write(littleBytes(paddingBlockSize - pairSize - 16));//padding size
        outputFile.write(littleBytes(PADDING_BLOCK_ID));

        //内容全是0，不需要写入
        outputFile.close();

    }

    private byte[] littleBytes(long number) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.asLongBuffer().put(number);
        return buffer.array();
    }

    private byte[] littleBytes(int number) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.asIntBuffer().put(number);
        return buffer.array();
    }

    private void differBytes() {
        try {
            FileInputStream fin = new FileInputStream(inputFile);
            FileInputStream fout = new FileInputStream(outPutFile);
            byte[] inBuffer = new byte[1024];
            byte[] outBuffer = new byte[1024];
            long index = 0;
            while (true) {
                int len = fin.read(inBuffer);
                fout.read(outBuffer);
                if (len != -1) {
                    index += len;
                }
                if (!Arrays.equals(inBuffer, outBuffer)) {
                    break;
                }
            }
            System.out.println("differ:" + index);
        } catch (Exception e) {

        }
    }


    public static ApkUtils.ApkSigningBlock getSigningBlock(DataSource inputApk) throws IOException, ZipFormatException, ApkSigningBlockNotFoundException {
        ApkUtils.ZipSections inputZipSections = ApkUtils.findZipSections(inputApk);
        return ApkUtils.findApkSigningBlock(inputApk, inputZipSections);
    }

    public static void dumpSigningBlock(DataSource block, DumpInfo info) throws IOException {
        long size = block.size();
        System.out.print("dump sign info: ");
        System.out.print("totalSize:" + size);

        //Apk Signing Block 大小，但是不包含本身，也就是实际Block大小需要+8
        ByteBuffer byteBuffer = block.getByteBuffer(0, (int) size).order(ByteOrder.LITTLE_ENDIAN);
        long blockSize = byteBuffer.getLong();
        System.out.println(" blockSize:" + blockSize);
        System.out.println();
        while (true) {
            try {
                long pairSize = byteBuffer.getLong();
                if (pairSize == blockSize) {
                    System.out.println("all pairs dumped");
                    break;
                }
                System.out.print("size=" + pairSize);
                int keyId = byteBuffer.getInt();
                System.out.printf(" id=0x%04x ", keyId);
                byte[] bytes = new byte[(int) (pairSize - 4)];
                System.out.printf(" bytes.len=%d ", bytes.length);

                byteBuffer.get(bytes);
                if (keyId == PADDING_BLOCK_ID) {
                    int position = byteBuffer.position();
                    if (info != null) {
                        info.paddingBlockOffset = position - pairSize - 8;
                        info.paddingBlockSize = pairSize + 8;
                    }
                    System.out.println("VERITY_PADDING_BLOCK_ID " + position);
                    printByte(bytes, true, 20);
                } else if (keyId == V2SchemeSigner.APK_SIGNATURE_SCHEME_V2_BLOCK_ID) {
                    System.out.println("V2_BLOCK_ID ");
                    printByte(bytes, true, 20);
                } else if (keyId == V3SchemeSigner.APK_SIGNATURE_SCHEME_V3_BLOCK_ID) {
                    System.out.println("V3_BLOCK_ID");
                    printByte(bytes, false, 20);
                } else if (keyId == CHANNEL_ID) {
                    System.out.println("CHANNEL_ID");
                    if (info != null) {
                        info.channel = bytes;
                    }
                    printByte(bytes, false, -1);
                } else {
                    System.out.println("UNKNOWN_BLOCK_ID");
                    printByte(bytes, true, 20);
                }
                System.out.println();

            } catch (Exception e) {
                System.out.println();
                e.printStackTrace();
                break;
            }

        }

        byte[] magic = new byte[16];
        byteBuffer.get(magic);
        System.out.println(new String(magic));
    }

    private static void printByte(byte[] bytes, boolean binary, int count) {
        if (binary) {
            if (count < 0 || count > bytes.length) {
                count = bytes.length;
            }
            for (int i = 0; i < count; i++) {
                System.out.printf("%02x ", bytes[i]);
            }
            System.out.println();
        } else {
            System.out.println(new String(bytes));
        }


    }

    public static String getChannel(String path) {
        try {
            DataSource inputApk = DataSources.asDataSource(new RandomAccessFile(new File(path), "r"));
            ApkUtils.ApkSigningBlock signingBlock = ApkChannel.getSigningBlock(inputApk);
            DumpInfo info = new DumpInfo();
            ApkChannel.dumpSigningBlock(signingBlock.getContents(), info);
            if (info.channel==null){
                return "";
            }
            return new String(info.channel);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }
}
