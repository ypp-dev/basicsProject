package com.ypp.core.network.model.home

import com.ypp.core.network.TopJsonBean
import com.ypp.datastore.UserInfo
import com.ypp.model.datastore.Banners
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHomeRepository @Inject constructor(
    private val homeDataSource: HomeDataSource ,
    //本地数据库等其他方式
): HomeRepository {
    override fun banner(): Flow<Banners> =homeDataSource.banner()
    override fun topJson(): Flow<TopJsonBean> =homeDataSource.topJson()
    override fun userInfo(): Flow<List<UserInfo>> =homeDataSource.userInfo()

    override suspend fun updateBanner() {
        homeDataSource.updateBanner()
    }

    override suspend fun updateTopJson(succeed: () -> Unit, fail: () -> Unit) {
       homeDataSource.updateTopJson(succeed = succeed,fail=fail)
    }

    override suspend fun addUserInfo(userInfo: UserInfo) {
       homeDataSource.addUserInfo(userInfo)
    }


}