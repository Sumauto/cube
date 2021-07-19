package com.sumauto.data;

import org.junit.Test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JDKTest {

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    public void test(){
       
    }

    @Test
    public void testRefer(){
        ReferenceQueue<ReferObj> queue=new ReferenceQueue<>();
        ReferObj obj=new ReferObj("Strong");
        WeakReference<ReferObj> weakRef=new WeakReference<>(new ReferObj("weak"),queue);
        WeakReference<ReferObj> weakRef2=new WeakReference<>(obj,queue);
        SoftReference<ReferObj> softRefer=new SoftReference<>(new ReferObj("soft"),queue);
        weakRef.enqueue();
        weakRef2.enqueue();


        System.out.println("strong:"+obj+" weak:"+weakRef.get()+" soft:"+softRefer.get());
        System.out.println("poll:"+queue.poll());
        System.gc();
        System.out.println("strong:"+obj+" weak:"+weakRef.get()+" soft:"+softRefer.get());
        System.out.println("poll:"+queue.poll());


    }

    @Test
    public void testInet() {
        try {
            InetAddress[] allByName = InetAddress.getAllByName("www.baidu.com");
            for (InetAddress address : allByName) {
                System.out.println(address.toString());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBin() {
        int number = 0;
        System.out.println(Integer.toBinaryString(number));
        System.out.println(Integer.toBinaryString(number ^ 255 ^ 255));
    }

    @Test
    public void testNumber() {
        System.out.println(Integer.toBinaryString(~0));
        System.out.println(Integer.toBinaryString(COUNT_BITS));
        System.out.println(Integer.toBinaryString(CAPACITY));
        System.out.println(Integer.toBinaryString(~CAPACITY));
        System.out.println(Integer.toBinaryString(RUNNING));
        System.out.println(Integer.toBinaryString(SHUTDOWN));
        System.out.println(Integer.toBinaryString(STOP));
        System.out.println(Integer.toBinaryString(TIDYING));
        System.out.println(Integer.toBinaryString(STOP));
        System.out.println(Integer.toBinaryString(TERMINATED));

        ExecutorService executorService = new ThreadPoolExecutor(4, 4,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            private final ThreadGroup group;
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            private final String namePrefix;

            {
                SecurityManager s = System.getSecurityManager();
                group = (s != null) ? s.getThreadGroup() :
                        Thread.currentThread().getThreadGroup();
                namePrefix = "pool-" +
                        2010 +
                        "-thread-";
            }

            @Override
            public Thread newThread(Runnable r) {
                System.out.println("new thread called");
                Thread t = new Thread(group, r,
                        namePrefix + threadNumber.getAndIncrement(),
                        0);
                if (t.isDaemon())
                    t.setDaemon(false);
                if (t.getPriority() != Thread.NORM_PRIORITY)
                    t.setPriority(Thread.NORM_PRIORITY);
                return t;
            }
        });
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " start-->");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " end-->");

                }
            });

        }


        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLock() {
        Lock firstLock = new ReentrantLock();
    }

    @Test
    public void testDemo() {

        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE) + " MIN");
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE).length() + " MIN length");
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE) + " MAX");
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE - 1) + " MIN-1:");
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE & Integer.MIN_VALUE) + " MIN&MAX");
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toOctalString(-1));
        System.out.println(Integer.toHexString(-1));
        System.out.println(Integer.toHexString(-2));
        int n1 = 20;
        n1 = moveAndLog(n1);
        n1 = moveAndLog(n1);
        n1 = moveAndLog(n1);
        n1 = moveAndLog(n1);

        int n2 = 20;
        n2 = moveAndLog2(n2);
        n2 = moveAndLog2(n2);
        n2 = moveAndLog2(n2);
        n2 = moveAndLog2(n2);

        int n3 = -20;
        n3 = moveAndLog(n3);
        n3 = moveAndLog(n3);
        n3 = moveAndLog(n3);
        n3 = moveAndLog(n3);
        n3 = moveAndLog(n3);

        int n4 = -20;
        n4 = moveAndLog2(n4);
        n4 = moveAndLog2(n4);
        n4 = moveAndLog2(n4);
        n4 = moveAndLog2(n4);
    }

    private int moveAndLog(int v) {
        int v1 = v >>> 1;
        System.out.println(v1 + ":" + Integer.toBinaryString(v) + " >>> " + Integer.toBinaryString(v1));
        return v1;
    }

    private int moveAndLog2(int v) {
        int v1 = v >> 1;
        System.out.println(v1 + ":" + Integer.toBinaryString(v) + " >> " + Integer.toBinaryString(v1));
        return v1;
    }

}
