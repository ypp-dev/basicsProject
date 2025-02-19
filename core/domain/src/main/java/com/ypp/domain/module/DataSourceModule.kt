package com.ypp.domain.module

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.ypp.domain.BannersSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule{
    @Provides
//    @Singleton
    fun provideBannerDataSource(
        @ApplicationContext appContext: Context,
        bannersSerializer: BannersSerializer,)=
        DataStoreFactory.create(
            serializer = bannersSerializer,
            corruptionHandler = null,
            produceFile={
                appContext.dataStoreFile("banners.pb")
            }
        )
}
