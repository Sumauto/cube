package com.sumauto.cube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.sumauto.helper.log.XLog;

import java.lang.ref.WeakReference;

public class LeakActivity extends AppCompatActivity {


    private final Handler mHandler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            XLog.d(MemoryLeakFragment.TAG,"LeakActivity handler "+msg.what);
            function();

        }
    };

    private static void leakCheck(Activity activity){
        WeakReference<Activity> weakReference=new WeakReference<>(activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (weakReference.get()!=null){
                    XLog.d(MemoryLeakFragment.TAG,"LeakActivity in refer ");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                XLog.d(MemoryLeakFragment.TAG,"LeakActivity no refer ");

            }
        }).start();
    }

    private void function(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {

                    mHandler.sendEmptyMessage(20);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        leakCheck(this);
    }
}