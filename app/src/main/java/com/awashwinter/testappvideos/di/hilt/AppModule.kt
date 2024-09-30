package com.awashwinter.testappvideos.di.hilt

import com.awashwinter.testappvideos.repository.CachedVideosRepository
import com.awashwinter.testappvideos.repository.DatabaseCachedVideosRepository
import com.awashwinter.testappvideos.repository.PexelRemoteVideosRepository
import com.awashwinter.testappvideos.repository.RemoteVideosRepository
import com.awashwinter.testappvideos.services.database.DatabaseCachedVideosService
import com.awashwinter.testappvideos.services.videos.api.PexelVideosApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCachedVideosRepository(databaseCachedVideosService: DatabaseCachedVideosService): CachedVideosRepository =
        DatabaseCachedVideosRepository(databaseCachedVideosService)

    @Provides
    @Singleton
    fun provideRemoteVideosRepository(pexelVideosApiService: PexelVideosApiService, cachedVideosRepository: CachedVideosRepository): RemoteVideosRepository =
        PexelRemoteVideosRepository(pexelVideosApiService, cachedVideosRepository)

}