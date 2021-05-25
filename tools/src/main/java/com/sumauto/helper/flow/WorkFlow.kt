package com.sumauto.helper.flow

import java.util.concurrent.Executors

class WorkFlow {

    companion object {
        @JvmStatic
        fun of(vararg task: Task<*>): WorkFlow {
            return WorkFlow().apply {
                works.addAll(task)
                works.forEach {
                    it.onAttach(this)
                }
            }
        }
    }

    private val works = mutableSetOf<Task<*>>()
    private val executor = Executors.newSingleThreadExecutor()

    fun start() {

        findAttachedTaskAndExecute()

    }

    fun onComplete(task: Task<*>) {
        println("${task.name} onComplete")
        findAttachedTaskAndExecute()
    }

    private fun findAttachedTaskAndExecute() {
        executor.execute {
            works.filter {
                it.state == Task.State.Attached
            }.forEach {
                if (it.canStart()) {
                    it.requestStart()
                }
            }
        }

    }

    fun finish() {
        works.clear()
    }

    @Suppress("UNCHECKED_CAST")
    fun <R> findTaskByName(name: String): Task<R>? = works.find {
        it.name == name
    } as Task<R>

    @Suppress("UNCHECKED_CAST")
    fun <R> findTaskById(id: Int): Task<R>? = works.find {
        it.id == id
    } as Task<R>?

}