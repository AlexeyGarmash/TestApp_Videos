package com.awashwinter.testappvideos.services.videos.api

import com.awashwinter.testappvideos.models.api.pexel.PexelVideosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelVideosApiService {

    @GET("videos/popular")
    suspend fun fetchVideos(
        @Query("per_page") perPage: Int = 11
    ): Response<PexelVideosResponse>

}