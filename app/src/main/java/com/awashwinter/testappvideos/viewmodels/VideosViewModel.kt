package com.awashwinter.testappvideos.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awashwinter.testappvideos.data.VideosDataSource
import com.awashwinter.testappvideos.models.VideoItem
import com.awashwinter.testappvideos.utils.TaskResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(private val videosDataSource: VideosDataSource): ViewModel() {

    private val mutableVideos = MutableLiveData<TaskResult<List<VideoItem>>>()
    private val mutableLoading = MutableLiveData<Boolean>()

    val liveDataVideos get() : LiveData<TaskResult<List<VideoItem>>> = mutableVideos
    val liveDataLoading get(): LiveData<Boolean> = mutableLoading

    fun getVideos() {
        mutableLoading.value = true
        viewModelScope.launch {
            val videosResult = videosDataSource.getLocalVideos()
            Log.d("[VideosViewModel]", "Videos LOCAL result: " + videosResult.data.toString())
            mutableVideos.postValue(videosResult)
            val videosResultRmt = videosDataSource.getRemoteVideos()
            Log.d("[VideosViewModel]", "Videos REMOTE result: " + videosResultRmt.data.toString())
            mutableLoading.postValue(false)
            mutableVideos.postValue(videosResultRmt)
        }
    }
}