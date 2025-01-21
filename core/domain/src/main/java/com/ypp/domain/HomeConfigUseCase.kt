package com.ypp.domain


import com.ypp.core.network.TopJsonBean
import com.ypp.core.network.bean.BannerBean
import com.ypp.core.network.model.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class HomeConfigUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    operator fun invoke() :Flow<HomeConfig>{
       return combine(homeRepository.banner(),homeRepository.topJson()){
                banner ,topJson->
            HomeConfig(banner = banner, topJson = topJson)
        }
    }


}
data class HomeConfig(
    val banner: BannerBean,
    val topJson:TopJsonBean
)