package com.awashwinter.testappvideos.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.awashwinter.testappvideos.database.entity.CachedVideoEntity

@Dao
interface CachedVideosDao {

    @Query("SELECT * FROM cached_vids")
    suspend fun getAllVideos(): List<CachedVideoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: CachedVideoEntity)

}