package com.ypp.core.network.model.home

import com.ypp.core.network.TopJsonBean
import com.ypp.core.network.bean.BannerBean
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {
    fun banner():Flow<BannerBean>
    fun topJson():Flow<TopJsonBean>
    suspend fun updateBanner()
    suspend fun updateTopJson(succeed:()->Unit,fail:()->Unit)

}