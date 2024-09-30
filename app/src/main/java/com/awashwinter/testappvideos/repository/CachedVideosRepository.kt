package com.awashwinter.testappvideos.repository

import com.awashwinter.testappvideos.database.entity.CachedVideoEntity
import com.awashwinter.testappvideos.models.VideoItem
import com.awashwinter.testappvideos.utils.TaskResult

interface CachedVideosRepository {

    suspend fun getVideos(): TaskResult<List<VideoItem>>
    suspend fun addVideoInfoToCache(videoItem: VideoItem)

}