package com.awashwinter.testappvideos.repository

import com.awashwinter.testappvideos.models.VideoItem
import com.awashwinter.testappvideos.models.api.pexel.PexelVideosResponse
import com.awashwinter.testappvideos.services.videos.api.PexelVideosApiService
import com.awashwinter.testappvideos.utils.TaskResult
import retrofit2.Response
import javax.inject.Inject

class PexelRemoteVideosRepository @Inject constructor(
    private val pexelVideosApiService: PexelVideosApiService,
    private val cachedVideosRepository: CachedVideosRepository
) : RemoteVideosRepository {

    private suspend fun convertToVideoItems(videosResponse: Response<PexelVideosResponse>): TaskResult<List<VideoItem>> {
        val resultsVideos = arrayListOf<VideoItem>()
        if(videosResponse.isSuccessful) {
            if(videosResponse.body() != null) {
                for(responseVideoItem in videosResponse.body()!!.videos) {
                    val videoUrl = responseVideoItem.videoFiles.first().link
                    val videoItem = VideoItem(
                        responseVideoItem.id?.toLong(), videoUrl, "Video #${responseVideoItem.id}", responseVideoItem.image
                    )
                    cachedVideosRepository.addVideoInfoToCache(videoItem)
                    resultsVideos.add(videoItem)
                }
                return TaskResult.Success(resultsVideos)
            } else {
                return TaskResult.Error("Response is empty!")
            }
        } else {
            return TaskResult.Error(videosResponse.message())
        }
    }

    override suspend fun getVideos(): TaskResult<List<VideoItem>> {
        try {
            val videosResponse = pexelVideosApiService.fetchVideos()
            return convertToVideoItems(videosResponse)
        } catch (ex: Exception) {
            return TaskResult.Error(ex.message)
        }
    }
}