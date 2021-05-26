package com.sumauto.helper.flow

import androidx.lifecycle.MediatorLiveData
import com.sumauto.helper.log.XLog

class WorkFlow(vararg task: Task<*>) {

    var myTask = task
    var blockBy: Array<out Task<*>>? = null
    val data = MediatorLiveData<Any>()
    var complete: Runnable? = null

    init {
        myTask.forEach {
            if (it.state.value != Task.State.Init) {
                throw IllegalStateException("${it.name} state must be State.Init")
            }
            it.state.value = Task.State.Attached
        }
    }

    companion object {
        @JvmStatic
        fun of(vararg task: Task<*>): WorkFlow {
            return WorkFlow(*task)
        }
    }

    fun blockBy(vararg blockBy: Task<*>): WorkFlow {
        this.blockBy = blockBy
        return this
    }

    fun run() {
        myTask.forEach {
            if (it.state.value == Task.State.Attached) {
                it.state.postValue(Task.State.Started)
                XLog.d(Works.TAG, "${it.name} start")
                it.run()
            }
        }

        if (blockBy != null) {

            blockBy!!.forEach {
                data.addSource(it.state) { state ->
                    if (state == Task.State.DONE) {
                        XLog.d(Works.TAG,"block by ${it.name} has end")
                        data.value=true
                        data.removeSource(it.state)
                    }

                }
            }
            data.observeForever {
                val find = blockBy!!.find {
                    it.state.value != Task.State.DONE
                }
                if (find == null) {
                    XLog.d(Works.TAG,"not find un done task,")
                    complete?.run()
                }else{
                    XLog.d(Works.TAG,"find un done task ${find.name}")
                }
            }
        }
    }


}