package com.awashwinter.testappvideos.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awashwinter.testappvideos.data.VideosDataSource
import com.awashwinter.testappvideos.models.VideoItem
import com.awashwinter.testappvideos.utils.DataLoadingState
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
    private val mutableLoading = MutableLiveData<DataLoadingState>()

    val liveDataVideos get() : LiveData<TaskResult<List<VideoItem>>> = mutableVideos
    val liveDataLoading get(): LiveData<DataLoadingState> = mutableLoading

    fun getVideos() {
        mutableLoading.value = DataLoadingState.LocalLoading
        viewModelScope.launch {
            val videosResult = videosDataSource.getLocalVideos()
            Log.d("[VideosViewModel]", "Videos LOCAL result: ${videosResult.data}")
            mutableVideos.postValue(videosResult)
            mutableLoading.postValue(DataLoadingState.RemoteLoading)
            val videosResultRmt = videosDataSource.getRemoteVideos()
            Log.d("[VideosViewModel]", "Videos REMOTE result: ${videosResultRmt.data}")
            mutableLoading.postValue(DataLoadingState.Complete)
            mutableVideos.postValue(videosResultRmt)
        }
    }
}