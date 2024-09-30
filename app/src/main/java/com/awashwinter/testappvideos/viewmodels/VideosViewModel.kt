package com.awashwinter.testappvideos.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awashwinter.testappvideos.data.VideosDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(private val videosDataSource: VideosDataSource): ViewModel() {


    fun getLocalVideos() {
        viewModelScope.launch {
            val videosResult = videosDataSource.getLocalVideos()
            Log.d("[VideosViewModel]", "Videos LOCAL result: " + videosResult.data.toString())
        }
    }

    fun getRemoteVideos() {
        viewModelScope.launch {
            val videosResult = videosDataSource.getRemoteVideos()
            Log.d("[VideosViewModel]", "Videos REMOTE result: " + videosResult.data.toString())
        }
    }

    fun getVideos() {
        viewModelScope.launch {
            val videosResult = videosDataSource.getLocalVideos()
            Log.d("[VideosViewModel]", "Videos LOCAL result: " + videosResult.data.toString())
            val videosResultRmt = videosDataSource.getRemoteVideos()
            Log.d("[VideosViewModel]", "Videos REMOTE result: " + videosResultRmt.data.toString())
        }
    }
}