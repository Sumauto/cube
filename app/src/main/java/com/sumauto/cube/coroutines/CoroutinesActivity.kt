package com.sumauto.cube.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.sumauto.cube.databinding.ActivityKtBinding
import com.sumauto.helper.log.XLog
import kotlinx.coroutines.*

class CoroutinesActivity : AppCompatActivity() {

    private lateinit var binding:ActivityKtBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityKtBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("", MODE_PRIVATE)
        sharedPreferences.edit(commit = true) {
            putBoolean("", true)
        }

        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            start()
        }


    }

    suspend fun start() {
        var firstStep = withContext(Dispatchers.IO) {
            updateLog("first start")

            kotlin.runCatching {
                Thread.sleep(2000)
            }
            updateLog("first end")

            return@withContext "firstStep done"
        }

        var secondStep = withContext(Dispatchers.IO) {
            updateLog("second start")
            kotlin.runCatching {

                Thread.sleep(2000)
            }

            updateLog("second end")
            return@withContext "secondStep done"
        }
    }


    private suspend fun sleepT() {
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                Thread.sleep(2000)
            }

        }
    }

    private fun updateLog(text: String) {
        XLog.d(text)
        runOnUiThread {
            binding.tvText.append(text)
            binding.tvText.append("\n")
        }

    }
}