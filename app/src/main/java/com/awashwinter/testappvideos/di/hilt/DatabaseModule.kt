package com.awashwinter.testappvideos.di.hilt

import android.content.Context
import androidx.room.Room
import com.awashwinter.testappvideos.base.Constants
import com.awashwinter.testappvideos.database.AppDatabase
import com.awashwinter.testappvideos.database.dao.CachedVideosDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            AppDatabase::class.java,
            Constants.APP_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCachedVideosDao(appDatabase: AppDatabase): CachedVideosDao {
        return  appDatabase.getCachedVideosDao()
    }


}