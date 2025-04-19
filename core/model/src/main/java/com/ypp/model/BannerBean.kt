package com.ypp.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerBean(
    @SerialName("data")
    val `data`: List<Data> = emptyList(),
    @SerialName("errorCode")
    val errorCode: Int=0,
    @SerialName("errorMsg")
    val errorMsg: String=""
) {
    @Serializable
    data class Data(
        @SerialName("desc")
        val desc: String,
        @SerialName("id")
        val id: Int,
        @SerialName("imagePath")
        val imagePath: String,
        @SerialName("isVisible")
        val isVisible: Int,
        @SerialName("order")
        val order: Int,
        @SerialName("title")
        val title: String,
        @SerialName("type")
        val type: Int,
        @SerialName("url")
        val url: String
    )
}