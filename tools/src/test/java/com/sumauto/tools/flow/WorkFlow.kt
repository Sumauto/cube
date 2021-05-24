package com.sumauto.helper.flow

class WorkFlow {

    companion object {
        @JvmStatic
        fun of(vararg task: Task): WorkFlow {
            return WorkFlow().apply {
                works.addAll(task)
            }
        }
    }

    private val works = mutableSetOf<Task>()

    fun start() {
        works.forEach {
            it.requestStart()
        }
    }

}