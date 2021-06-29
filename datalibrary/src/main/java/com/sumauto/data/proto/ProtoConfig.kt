package com.sumauto.data.proto

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.dataStore by preferencesDataStore(name = "settings")

@Suppress("unused", "UNUSED_VARIABLE")
object ProtoConfig {

    fun <T> get(context: Context, key: Preferences.Key<T>, defaultValue: T): T? {
        return runBlocking {

            context.dataStore.data.map {
                it[key] ?: defaultValue
            }.first()
        }
    }

    fun <T> getFlow(context: Context, key: Preferences.Key<T>): Flow<T?> {
        return context.dataStore.data.map {
            it[key]
        }
    }

    fun <T> set(context: Context, key: Preferences.Key<T>, value: T) {
        runBlocking {
            context.dataStore.edit {
                it[key] = value
            }
        }
    }

    suspend fun startReadSuspend(context: Context) {
        val show = context.settingsDataStore.data.map {
            it.showCompleted
        }.first()
        val value = context.dataStore.data.map {
            it[intPreferencesKey("haha")]
        }.first()
    }

    fun startReadSync(context: Context) {
        val show = runBlocking {
            context.settingsDataStore.data.map {
                it.showCompleted
            }.first()
        }
    }

    suspend fun startWrite(context: Context) {
        context.settingsDataStore.updateData {
            it.toBuilder()
                .setShowCompleted(false)
                .build()
        }
    }

    fun startWriteSync(context: Context) {
        runBlocking {

            context.settingsDataStore.updateData {
                it.toBuilder()
                    .setShowCompleted(false)
                    .build()
            }
        }

    }
}