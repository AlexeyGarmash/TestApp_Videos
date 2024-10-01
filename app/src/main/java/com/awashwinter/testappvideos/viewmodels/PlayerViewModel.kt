package com.awashwinter.testappvideos.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import com.awashwinter.testappvideos.models.VideoItem

class PlayerViewModel : ViewModel() {

    private val mutableCurrentVideoDuration = MutableLiveData<Long>(0)
    private val mutablePlayerPlaylist = MutableLiveData<MutableList<MediaItem>>(mutableListOf())

    val liveDataCurrentVideoDuration get() : LiveData<Long> = mutableCurrentVideoDuration
    val liveDataPlayerPlaylist get() : LiveData<MutableList<MediaItem>> = mutablePlayerPlaylist

    fun setCurrentDuration(duration: Long?) {
        duration?.let {
            mutableCurrentVideoDuration.value = it
        }

    }

    fun generateMediaPlaylist(videos: List<VideoItem>) {

        val currentMediaList = mutablePlayerPlaylist.value
        currentMediaList?.let {
            it.clear()
            for (videoItem in videos) {
                it.add(MediaItem.fromUri(Uri.parse(videoItem.url)))
            }
            mutablePlayerPlaylist.postValue(it)
            Log.d("generatePlayList", "generatePlayList: ${it.size}")
        }
    }
}