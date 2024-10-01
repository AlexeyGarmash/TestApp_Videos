package com.awashwinter.testappvideos.viewmodels

import android.util.Log
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
    val currentMediaData get(): VideoItem? {
        return liveDataSelectedVideoPosition.value?.let { liveDataVideoPlaylist.value?.get(it) }
    }


    fun setSelectedVideoPosition(position: Int) {
        mutableSelectedVideoPosition.value = position
    }

    fun setPlaylist(videosList: List<VideoItem>) {
        mutableVideoPlaylist.value = videosList
    }

    fun setNextVideo() {
        val currentIndex = liveDataSelectedVideoPosition.value
        currentIndex?.let {
            liveDataVideoPlaylist.value?.let { playlist ->
                if (it + 1 < playlist.size) {
                    mutableSelectedVideoPosition.value = it + 1
                }
            }
        }
    }

    fun setPrevVideo() {
        val currentIndex = liveDataSelectedVideoPosition.value
        currentIndex?.let {
            liveDataVideoPlaylist.value?.let { playlist ->
                if (it - 1 >= 0) {
                    mutableSelectedVideoPosition.value = it - 1
                }
            }
        }
    }

}