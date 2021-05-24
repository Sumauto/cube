package com.sumauto.cube.shell

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket

class SocketClient(var cmd: String, var mOnServiceSend: OnServiceSend) {
    private val TAG = "SocketClient"
    private val HOST = "127.0.0.1"
    var printWriter: PrintWriter? = null
    var bufferedReader: BufferedReader? = null
    var port = 4521

    //线程类
    internal inner class CreateServerThread(s: Socket) : Thread() {
        var socket: Socket
        var inputStreamReader: InputStreamReader? = null
        var reader: BufferedReader? = null
        override fun run() {
            try {
                // 打印读入一字符串并回调
                try {
                    inputStreamReader = InputStreamReader(socket.getInputStream())
                    reader = BufferedReader(inputStreamReader)
                    var line: String? = null
                    while (reader!!.readLine().also { line = it } != null) {
                        if (line != null) mOnServiceSend.getSend(line)
                    }
                    Log.d(TAG, "客户端接收解析服务器的while循环结束")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(TAG, "客户端接收解析服务器的Threadcatch块执行：$e")
                }
                bufferedReader!!.close()
                printWriter!!.close()
                socket.close()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d(TAG, "socket 接收线程发生错误：$e")
            }
        }

        init {
            Log.d(TAG, "创建了一个新的连接线程")
            socket = s
            start()
        }
    }

    fun send(cmd: String?) {
        printWriter!!.println(cmd)
        // 刷新输出流，使Server马上收到该字符串
        printWriter!!.flush()
    }

    interface OnServiceSend {
        fun getSend(result: String?)
    }

    init {
        try {
            Log.d(TAG, "与service进行socket通讯,地址=$HOST:$port")
            val socket = Socket()
            socket.connect(InetSocketAddress(HOST, port), 3000) //设置连接请求超时时间3 s
            socket.soTimeout = 3000
            Log.d(TAG, "与service进行socket通讯,超时为：" + 3000)
            // 由Socket对象得到输出流，并构造PrintWriter对象
            printWriter = PrintWriter(socket.getOutputStream(), true)
            // 由Socket对象得到输入流，并构造相应的BufferedReader对象
            bufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))
            CreateServerThread(socket)
            send(cmd)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "与service进行socket通讯发生错误$e")
            mOnServiceSend.getSend("###ShellRunError:$e")
        }
    }
}