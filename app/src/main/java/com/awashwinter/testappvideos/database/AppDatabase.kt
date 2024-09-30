package com.awashwinter.testappvideos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.awashwinter.testappvideos.database.dao.CachedVideosDao
import com.awashwinter.testappvideos.database.entity.CachedVideoEntity

@Database(entities = [CachedVideoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCachedVideosDao(): CachedVideosDao
}