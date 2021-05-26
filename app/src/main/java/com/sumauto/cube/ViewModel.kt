package com.sumauto.cube

import android.app.Application
import android.os.Process
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sumauto.helper.log.XLog
import com.sumauto.constants.LogTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel(application: Application) : AndroidViewModel(application) {
    fun start() {
        XLog.d(LogTag.COROUTINES, "viewModelScope.start:" + Thread.currentThread().id)
        viewModelScope.launch {
            XLog.d(LogTag.COROUTINES, "viewModelScope.launch:" + Thread.currentThread().id)
            val makeRequest = makeRequest()
            XLog.d(LogTag.COROUTINES, "makeRequest:$makeRequest")

            val another = withContext(Dispatchers.Default) {
                XLog.d(LogTag.COROUTINES, "another request start" + Process.myTid())
                Thread.sleep(3000)
                XLog.d(LogTag.COROUTINES, "another request end" + Thread.currentThread().id)
                "hello another"
            }
            XLog.d(LogTag.COROUTINES, "get another result :$another")


        }
    }

    private suspend fun makeRequest(): Boolean {
        return withContext(Dispatchers.IO) {
            XLog.d(LogTag.COROUTINES, "withContext" + Thread.currentThread().id)
            Thread.sleep(4000)
            XLog.d(LogTag.COROUTINES, "withContext sleep")

            return@withContext true
        }
    }
}