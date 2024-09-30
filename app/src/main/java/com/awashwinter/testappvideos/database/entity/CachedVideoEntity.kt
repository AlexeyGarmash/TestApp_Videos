package com.awashwinter.testappvideos.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.awashwinter.testappvideos.base.Constants

@Entity(tableName = Constants.CACHED_VIDEOS_DB_TABLE_NAME)
data class CachedVideoEntity(
    @PrimaryKey val id: Long,
    val url: String?,
    val name: String?,
    val imageUrl: String?
)
