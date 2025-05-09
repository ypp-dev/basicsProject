package com.ypp.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopJsonBean(
    @SerialName("data")
    val `data`: List<Data> = emptyList(),
    @SerialName("errorCode")
    val errorCode: Int=0,
    @SerialName("errorMsg")
    val errorMsg: String="") {

    @Serializable
    data class Data(
        @SerialName("adminAdd")
        val adminAdd: Boolean,
        @SerialName("apkLink")
        val apkLink: String,
        @SerialName("audit")
        val audit: Int,
        @SerialName("author")
        val author: String,
        @SerialName("canEdit")
        val canEdit: Boolean,
        @SerialName("chapterId")
        val chapterId: Int,
        @SerialName("chapterName")
        val chapterName: String,
        @SerialName("collect")
        val collect: Boolean,
        @SerialName("courseId")
        val courseId: Int,
        @SerialName("desc")
        val desc: String,
        @SerialName("descMd")
        val descMd: String,
        @SerialName("envelopePic")
        val envelopePic: String,
        @SerialName("fresh")
        val fresh: Boolean,
        @SerialName("host")
        val host: String,
        @SerialName("id")
        val id: Int,
        @SerialName("isAdminAdd")
        val isAdminAdd: Boolean,
        @SerialName("link")
        val link: String,
        @SerialName("niceDate")
        val niceDate: String,
        @SerialName("niceShareDate")
        val niceShareDate: String,
        @SerialName("origin")
        val origin: String,
        @SerialName("prefix")
        val prefix: String,
        @SerialName("projectLink")
        val projectLink: String,
        @SerialName("publishTime")
        val publishTime: Long,
        @SerialName("realSuperChapterId")
        val realSuperChapterId: Int,
        @SerialName("selfVisible")
        val selfVisible: Int,
        @SerialName("shareDate")
        val shareDate: Long,
        @SerialName("shareUser")
        val shareUser: String,
        @SerialName("superChapterId")
        val superChapterId: Int,
        @SerialName("superChapterName")
        val superChapterName: String,
        @SerialName("tags")
        val tags: List<String>,
        @SerialName("title")
        val title: String,
        @SerialName("type")
        val type: Int,
        @SerialName("userId")
        val userId: Int,
        @SerialName("visible")
        val visible: Int,
        @SerialName("zan")
        val zan: Int
    )
}