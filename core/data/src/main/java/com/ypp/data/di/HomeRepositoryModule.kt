package com.ypp.data.di

import com.ypp.data.repository.NetWorkHomeDataSource
import com.ypp.data.repository.NetworkHomeRepository
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
    ): com.ypp.data.repository.HomeRepository
    @Binds
    fun bindingHomeDataSource(
       netWorkHomeDataSource: NetWorkHomeDataSource
    ): com.ypp.data.repository.HomeDataSource
}



