package com.ypp.domain.module

import com.ypp.core.network.model.home.HomeDataSource
import com.ypp.core.network.model.home.HomeRepository
import com.ypp.core.network.model.home.NetWorkHomeDataSource
import com.ypp.core.network.model.home.NetworkHomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface HomeRepositoryModule {
    @Binds
    fun bindingHomeRepository(
        homeRepository: NetworkHomeRepository
    ): HomeRepository
    @Binds
    fun bindingHomeDataSource(
       netWorkHomeDataSource: NetWorkHomeDataSource
    ):HomeDataSource
}



