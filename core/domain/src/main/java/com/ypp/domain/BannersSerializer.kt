package com.ypp.domain

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.ypp.model.datastore.Banners
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject


class BannersSerializer @Inject constructor() :Serializer<Banners> {
    override val defaultValue: Banners
        get() = Banners.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Banners =
        try {

            Banners.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }


    override suspend fun writeTo(t: Banners, output: OutputStream) {
        t.writeTo(output)
    }
}