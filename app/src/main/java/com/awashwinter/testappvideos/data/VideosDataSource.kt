package com.awashwinter.testappvideos.data

import com.awashwinter.testappvideos.models.VideoItem
import com.awashwinter.testappvideos.repository.CachedVideosRepository
import com.awashwinter.testappvideos.repository.RemoteVideosRepository
import com.awashwinter.testappvideos.utils.TaskResult
import javax.inject.Inject

class VideosDataSource @Inject constructor(private val remoteVideosRepository: RemoteVideosRepository, private val cachedVideosRepository: CachedVideosRepository) {

    suspend fun getRemoteVideos() : TaskResult<List<VideoItem>> {
        return remoteVideosRepository.getVideos()
    }

    suspend fun getLocalVideos() : TaskResult<List<VideoItem>> {
        return cachedVideosRepository.getVideos()
    }

}