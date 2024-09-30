package com.awashwinter.testappvideos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awashwinter.testappvideos.models.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

class ShareViewModel : ViewModel() {

    private val mutableSelectedVideoPosition = MutableLiveData<Int>()
    private val mutableVideoPlaylist = MutableLiveData<List<VideoItem>>()

    val liveDataSelectedVideoPosition get() : LiveData<Int> = mutableSelectedVideoPosition
    val liveDataVideoPlaylist get() : LiveData<List<VideoItem>> = mutableVideoPlaylist


    fun setSelectedVideoPosition(position: Int) {
        mutableSelectedVideoPosition.value = position
    }

    fun setPlaylist(videosList: List<VideoItem>) {
        mutableVideoPlaylist.value = videosList
    }

}