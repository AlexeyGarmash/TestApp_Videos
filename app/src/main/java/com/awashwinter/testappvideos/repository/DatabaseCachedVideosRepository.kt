package com.awashwinter.testappvideos.repository

import androidx.room.util.ViewInfo
import com.awashwinter.testappvideos.database.entity.CachedVideoEntity
import com.awashwinter.testappvideos.models.VideoItem
import com.awashwinter.testappvideos.services.database.DatabaseCachedVideosService
import com.awashwinter.testappvideos.utils.TaskResult
import java.lang.Exception
import javax.inject.Inject

class DatabaseCachedVideosRepository @Inject constructor(private val databaseCachedVideosService: DatabaseCachedVideosService) : CachedVideosRepository {

    override suspend fun getVideos(): TaskResult<List<VideoItem>> {
        try {
            val databaseCacheResult = databaseCachedVideosService.getAllVideos()
            return convertToVideoItems(databaseCacheResult)
        } catch (ex: Exception) {
            return TaskResult.Error(ex.message)
        }
    }

    override suspend fun addVideoInfoToCache(videoItem: VideoItem) {
        if(videoItem.id != null) {
            databaseCachedVideosService.updateVideo(CachedVideoEntity(videoItem.id, videoItem.url, videoItem.name, videoItem.imagePreviewUrl))
        }
    }

    private fun convertToVideoItems(databaseCacheResult: List<CachedVideoEntity>): TaskResult<List<VideoItem>> {
        val videosList = arrayListOf<VideoItem>()
        databaseCacheResult.map {
            videosList.add(VideoItem(it.id, it.url, it.name, it.imageUrl))
        }
        return TaskResult.Success(videosList)
    }
}