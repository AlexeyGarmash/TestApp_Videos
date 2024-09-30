package com.awashwinter.testappvideos.repository

import com.awashwinter.testappvideos.models.VideoItem
import com.awashwinter.testappvideos.utils.TaskResult

interface RemoteVideosRepository {

    suspend fun getVideos(): TaskResult<List<VideoItem>>
}