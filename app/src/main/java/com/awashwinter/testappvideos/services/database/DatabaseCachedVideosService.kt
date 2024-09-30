package com.awashwinter.testappvideos.services.database

import com.awashwinter.testappvideos.database.dao.CachedVideosDao
import com.awashwinter.testappvideos.database.entity.CachedVideoEntity
import javax.inject.Inject

class DatabaseCachedVideosService @Inject constructor(
    private val cachedVideosDao: CachedVideosDao
) {
    suspend fun getAllVideos(): List<CachedVideoEntity> {
        return cachedVideosDao.getAllVideos()
    }

    suspend fun updateVideo(cachedVideoEntity: CachedVideoEntity) {
        cachedVideosDao.insertVideo(cachedVideoEntity)
    }
}