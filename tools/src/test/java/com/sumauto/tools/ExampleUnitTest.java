package com.sumauto.tools;

import com.sumauto.helper.flow.Task;
import com.sumauto.helper.flow.WorkFlow;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {



    @Test
    public void addition_isCorrect() {
        testTask();

    }

    private static class RandomFinishTask implements Runnable {

        private Task<Boolean> task;
        private int from, to;

        public RandomFinishTask(Task<Boolean> task, int from, int to) {
            this.task = task;
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            try {
                long sleepSeconds = (from + new Random().nextInt(to - from)) * 1000;
                System.out.println(task.getName()+" will finish in "+sleepSeconds);
                Thread.sleep(sleepSeconds);
                task.done(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
                task.done(false);
            }
            System.out.println(task.getName()+" done ");

        }
    }

    ExecutorService executorService= Executors.newFixedThreadPool(3);

    private void testTask() {
        Task<Boolean> lottie = new Task<Boolean>("lottie",1) {
            @Override
            public void onStart() {
                //3-5秒
                executorService.execute(new RandomFinishTask(this, 3, 5));
            }
        };
        Task<Boolean> organic = new Task<Boolean>("organic",1) {
            @Override
            public void onStart() {
                //监听归因结果
                executorService.execute(new RandomFinishTask(this, 1, 10));

            }
        };
        Task<Boolean> wallPaper = new Task<Boolean>("wallPaper",1) {

            @Override
            public void onStart() {
                executorService.execute(new RandomFinishTask(this, 5, 10));

            }

            @Override
            public boolean canStart() {
                return lottie.isDone();
            }
        };


        Task<Boolean> splashLoad = new Task<Boolean>("splashLoad",1){
            @Override
            public void onStart() {
                executorService.execute(new RandomFinishTask(this, 1, 5));

            }
        };
        Task<Boolean> splashShow = new Task<Boolean>("splashShow",1) {

            @Override
            public void onStart() {
                done(true);
            }

            @Override
            public boolean canStart() {
                return lottie.isDone() && organic.isDone() && wallPaper.isDone() && splashLoad.isDone();
            }
        };

        Task<Boolean> splashClose=new Task<Boolean>("splashClose",1){
            @Override
            public void onStart() {

                executorService.execute(new RandomFinishTask(this, 1, 5));
            }

            @Override
            public boolean canStart() {
                return splashShow.isDone();
            }
        };

        WorkFlow workFlow = WorkFlow.of(wallPaper, splashLoad, splashShow, organic, lottie,splashClose);

        workFlow.start();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}