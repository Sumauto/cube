package com.sumauto.helper.flow

import androidx.lifecycle.MutableLiveData

abstract class Task<R>(var name: String = "", var id: Int = 0) {

    enum class State {
        Init, Attached, Started, DONE
    }

    var state: MutableLiveData<State> = MutableLiveData(State.Init)
    var result: R? = null

    abstract fun run()

    fun finish(result: R) {
        state.postValue(State.DONE)
        this.result = result
    }

}