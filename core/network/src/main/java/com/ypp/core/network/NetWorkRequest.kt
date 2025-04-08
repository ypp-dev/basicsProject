package com.ypp.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ypp.core.network.bean.BannerBean
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetWorkClient @Inject constructor():WanAndroidApi{

    val wanAndroid=
        Retrofit.Builder()
            .baseUrl(WanAndroidApi.BaseUrl)
            .client( OkHttpClient.Builder()
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .callTimeout(30,TimeUnit.SECONDS)
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
//
                            setLevel(HttpLoggingInterceptor.Level.BODY)
//
                        },
                )
                .build())
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(WanAndroidApi::class.java)
    //获取Banner的数据
    override suspend fun banner(): BannerBean = wanAndroid.banner()

    override suspend fun topJson():TopJsonBean=wanAndroid.topJson()


}