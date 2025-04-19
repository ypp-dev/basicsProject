package com.ypp.domain


import com.ypp.datastore.UserInfo
import com.ypp.model.datastore.Banners
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class HomeConfigUseCase @Inject constructor(
    private val homeRepository: com.ypp.data.repository.HomeRepository
) {

    operator fun invoke() :Flow<HomeConfig>{
       return combine(
           homeRepository.banner(),
           homeRepository.topJson(),
           homeRepository.userInfo()
       ){
                banner ,topJson,userInfo->
            HomeConfig(banner = banner,
                topJson = topJson,
                userInfo=userInfo
            )
        }
    }


}
data class HomeConfig(
    val banner: Banners,
    val topJson: com.ypp.model.TopJsonBean,
    val userInfo: List<UserInfo>
)