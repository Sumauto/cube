package com.sumauto.cube.flow

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sumauto.cube.R
import com.sumauto.helper.flow.Task
import com.sumauto.helper.flow.WorkFlow.Companion.of
import com.sumauto.helper.flow.Works
import com.sumauto.helper.flow.Works.Companion.newInstance
import com.sumauto.helper.log.XLog
import java.util.*
import java.util.concurrent.Executors


class WorkFlowTest : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_flow_test)

        testTask()

    }


    private class RandomFinishTask(
        private val task: Task<Boolean>,
        private val from: Int,
        private val to: Int
    ) :
        Runnable {

        override fun run() {
            try {
                val sleepSeconds = ((from + Random().nextInt(to - from)) * 1000).toLong()
                XLog.d(Works.TAG, task.name + " will finish in " + sleepSeconds)
                Thread.sleep(sleepSeconds)
                task.finish(true)
            } catch (e: InterruptedException) {
                e.printStackTrace()
                task.finish(false)
            }
            XLog.d(Works.TAG, task.name + " done ")
        }
    }

    var backgroundTask = Executors.newFixedThreadPool(3)

    var handler = Handler(Looper.getMainLooper())
    private fun executeOnMain(task: Task<Boolean>, from: Int, to: Int) {
        val sleepSeconds = (from..to).random() * 1000L
        XLog.d(Works.TAG, task.name + " will finish in " + sleepSeconds)
        handler.postDelayed({
            XLog.d(Works.TAG, task.name + " time over in " + sleepSeconds)
            task.finish(true)
        }, sleepSeconds)
    }


    private fun testTask() {
        val lottie: Task<Boolean> = object : Task<Boolean>("lottie", 1) {

            override fun run() {
                //3-5秒
                executeOnMain(this, 3, 5)
            }
        }
        val organic: Task<Boolean> = object : Task<Boolean>("organic", 1) {
            override fun run() {
                //监听归因结果
                executeOnMain(this, 1, 10)

            }
        }
        val wallPaper: Task<Boolean> = object : Task<Boolean>("wallPaper", 1) {
            override fun run() {
                executeOnMain(this, 5, 10)

            }
        }
        val splashLoad: Task<Boolean> = object : Task<Boolean>("splashLoad", 1) {
            override fun run() {
                backgroundTask.execute(RandomFinishTask(this, 1, 5))
            }
        }
        val splashShow: Task<Boolean> = object : Task<Boolean>("splashShow", 1) {
            override fun run() {
                finish(true)
            }
        }
        val splashClose: Task<Boolean> = object : Task<Boolean>("splashClose", 1) {
            override fun run() {
                executeOnMain(this, 1, 5)
            }
        }
        newInstance()
            .addFlow(of(lottie, organic, splashLoad).blockBy(lottie))
            .addFlow(of(wallPaper).blockBy(wallPaper, splashLoad,organic))
            .addFlow(of(splashShow).blockBy(splashShow))
            .addFlow(of(splashClose))
            .start()

    }
}