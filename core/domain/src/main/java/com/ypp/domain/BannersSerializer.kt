package com.ypp.domain

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.ypp.model.datastore.Banners
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

//// 定义 DataStore 文件名
//private const val USER_PREFERENCES_NAME = "user_preferences"
//private const val DATA_STORE_FILE_NAME = "user_prefs.pb"
//private const val SORT_ORDER_KEY = "sort_order"
//
// val Context.userPreferencesStore: DataStore<Banners> by dataStore(
//    fileName = DATA_STORE_FILE_NAME,
//    serializer = UserPreferencesSerializer
//)
//
//object UserPreferencesSerializer : Serializer<Banners> {
//    override val defaultValue: Banners = Banners.getDefaultInstance()
//    override suspend fun readFrom(input: InputStream): Banners {
//        try {
//            return Banners.parseFrom(input)
//        } catch (exception: InvalidProtocolBufferException) {
//            throw CorruptionException("Cannot read proto.", exception)
//        }
//    }
//
//    override suspend fun writeTo(t: Banners, output: OutputStream) = t.writeTo(output)
//}
class BannersSerializer @Inject constructor() :Serializer<Banners> {
    override val defaultValue: Banners
        get() = Banners.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Banners =
        try {
            // readFrom is already called on the data store background thread
            Banners.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }


    override suspend fun writeTo(t: Banners, output: OutputStream) {
        t.writeTo(output)
    }
}