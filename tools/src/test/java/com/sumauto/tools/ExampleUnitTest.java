package com.sumauto.tools;

import com.sumauto.helper.flow.Condition;
import com.sumauto.helper.flow.Task;
import com.sumauto.helper.flow.WorkFlow;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    static class DemoTask extends Task {
        public DemoTask(@NotNull String name) {
            super(name);
        }

        @Override
        public void onStart() {
            super.onStart();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    done();
                }
            }).start();
        }
    }

    private void testDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
            Date today = dateFormat.parse("20210531");
            Date fromDate = dateFormat.parse(dateStr);
            System.out.println(dateStr + " is yesterday of "+dateFormat.format(today)+" "+DateUt.isYesterday2(fromDate,today) );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addition_isCorrect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                testTask();
            }
        }).start();
        testDate("20210519");
        testDate("20210518");
        testDate("20200530");
        testDate("20200531");
        testDate("20200601");

        testDate("20210530");
        testDate("20210531");
        testDate("20210601");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testTask() {
        Task task1 = new DemoTask("t1");
        Task task2 = new DemoTask("t2");
        Task task3 = new DemoTask("t3");

        task1.setCondition(new Condition() {
            @Override
            public boolean canStart() {
                return true;
            }
        });

        task2.setCondition(new Condition() {
            @Override
            public boolean canStart() {
                return task1.isDone();
            }
        });

        task3.setCondition(new Condition() {
            @Override
            public boolean canStart() {
                return task2.isDone();
            }
        });

        WorkFlow.of(task1, task2, task3)
                .start();
    }


}