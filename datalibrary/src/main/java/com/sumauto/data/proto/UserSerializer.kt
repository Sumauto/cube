package com.sumauto.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.codelab.android.datastore.UserPreferences
import java.io.InputStream
import java.io.OutputStream

object UserSerializer : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences = UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        try {
            return UserPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: UserPreferences,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.settingsDataStore: DataStore<UserPreferences> by dataStore(
    fileName = "user_prefs.proto",
    serializer = UserSerializer
)
