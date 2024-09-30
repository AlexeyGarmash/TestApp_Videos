package com.awashwinter.testappvideos.di.hilt

import com.awashwinter.testappvideos.base.Constants
import com.awashwinter.testappvideos.services.videos.api.HeadersInterceptor
import com.awashwinter.testappvideos.services.videos.api.PexelVideosApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitPexelVideosModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                provideHeaderInterceptor()
            )
            .build()
    }

    @Provides
    fun provideHeaderInterceptor(): HeadersInterceptor {
        return HeadersInterceptor()
    }


    @Provides
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.PEXEL_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PexelVideosApiService =
        retrofit.create(PexelVideosApiService::class.java)
}