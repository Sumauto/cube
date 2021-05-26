package com.sumauto.cube.shell

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.sumauto.cube.R
import com.sumauto.cube.log.LogFragment
import kotlinx.coroutines.*
import java.io.PrintWriter
import java.lang.Runnable
import java.net.InetSocketAddress
import java.net.Socket


class ShellActivity : AppCompatActivity() {
    companion object {
        const val TAG = "Shell"
    }

    private val logFragment = LogFragment.newInstance(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shell)
        findViewById<View>(R.id.btnInstall).setOnClickListener {
            runCmd("pm install komee_dev_1.0.19_20200413210823.apk")
        }

        findViewById<View>(R.id.btnHeart).setOnClickListener {
            runCmd("###AreYouOK")
        }

        findViewById<View>(R.id.btnUninstall).setOnClickListener {
            runCmd("pm uninstall com.webeye.myapplication")
        }
        supportFragmentManager.beginTransaction()
                .add(R.id.logContent, logFragment)
                .commit()
    }

    private fun log(text: String) {
        Log.d(TAG, text)
        runOnUiThread {
            logFragment.addLog(text)
        }
    }

    private fun runCmd(cmd: String) {
        log("cmd:$cmd")
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            SocketClient(cmd, object : SocketClient.OnServiceSend {
                override fun getSend(result: String?) {
                    log("remote:$result")
                }
            })
        }

//        scope.launch {
//            withContext(Dispatchers.IO) {
//                val runCatching = kotlin.runCatching {
//                    val socket = Socket()
//                    socket.connect(InetSocketAddress("127.0.0.1", 4521), 3000) //设置连接请求超时时间3 s
//                    socket.soTimeout = 3000
//
//                    Log.d(TAG, "start write")
//                    val writer = PrintWriter(socket.getOutputStream(), true)
//                    writer.write(cmd)
//                    writer.flush()
//
//                    Log.d(TAG, "start read")
//                    val reader = socket.getInputStream().bufferedReader()
//                    var line: String?
//                    while (reader.readLine().also { line = it } != null) {
//                        Log.d(TAG, "line:$line")
//                        runOnUiThread {
//                            logFragment.addLog(line!!)
//                        }
//
//                    }
//
//
//                }
//                Log.d(TAG, "runCatching:$runCatching")
//
//
//            }
//        }

//        Thread(Runnable {
//            SocketClient(cmd, object : SocketClient.OnServiceSend {
//                override fun getSend(result: String?) {
//                    Log.d(TAG, "line:$result")
//                    runOnUiThread {
//                        logFragment.addLog(result!!)
//                    }
//
//                }
//            })
//        }).start()

    }
}