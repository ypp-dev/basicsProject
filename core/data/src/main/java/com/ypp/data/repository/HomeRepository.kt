package com.ypp.data.repository

import com.ypp.datastore.UserInfo
import com.ypp.model.datastore.Banners
import kotlinx.coroutines.flow.Flow

/*
* 首页数据仓库
* */
interface HomeRepository {
    fun banner():Flow<Banners>
    fun topJson():Flow<com.ypp.model.TopJsonBean>
    fun userInfo():Flow<List<UserInfo>>
    suspend fun updateBanner():Result<com.ypp.model.BannerBean>
    suspend fun updateTopJson(succeed:()->Unit,fail:()->Unit)
    suspend fun addUserInfo(userInfo: UserInfo)

}