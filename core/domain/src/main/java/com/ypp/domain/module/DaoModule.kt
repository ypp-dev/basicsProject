package com.ypp.domain.module

import com.ypp.datastore.dao.UserDao
import com.ypp.datastore.database.UserInfoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesTopicsDao(
        userInfoDatabase: UserInfoDatabase,
    ): UserDao = userInfoDatabase.userDao()
}