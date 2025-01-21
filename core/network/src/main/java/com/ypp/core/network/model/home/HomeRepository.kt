package com.ypp.core.network.model.home

import com.ypp.core.network.TopJsonBean
import com.ypp.core.network.bean.BannerBean
import kotlinx.coroutines.flow.Flow

/*
* 首页数据仓库
* */
interface HomeRepository {
    fun banner():Flow<BannerBean>
    fun topJson():Flow<TopJsonBean>
    suspend fun updateBanner()
    suspend fun updateTopJson(succeed:()->Unit,fail:()->Unit)

}