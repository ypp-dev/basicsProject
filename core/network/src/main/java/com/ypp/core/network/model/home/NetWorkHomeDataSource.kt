package com.ypp.core.network.model.home

import androidx.datastore.core.DataStore
import com.ypp.core.network.NetWorkClient
import com.ypp.core.network.TopJsonBean
import com.ypp.core.network.request
import com.ypp.model.datastore.Banners
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class NetWorkHomeDataSource @Inject constructor(
    private val netWorkClient: NetWorkClient,
    private val banners: DataStore<Banners>,
) :HomeDataSource{
    private val _topJsonFlow= MutableStateFlow(TopJsonBean())
    override fun banner(): Flow<Banners> =banners.data

    override fun topJson(): Flow<TopJsonBean> =_topJsonFlow.asStateFlow()

    override suspend fun updateBanner() {
        request{
            netWorkClient.banner()
        }.catch {
            it.printStackTrace()
        }.collectLatest {newDate->

        }
    }


    override suspend fun updateTopJson(succeed: () -> Unit, fail: () -> Unit) {
        try {
            val response=netWorkClient.topJson()
            if (response.errorCode==0){
                _topJsonFlow.emit(response)
                succeed()
            }else{
                fail()
            }

        }catch (e:Exception){
            fail()
        }


    }
}