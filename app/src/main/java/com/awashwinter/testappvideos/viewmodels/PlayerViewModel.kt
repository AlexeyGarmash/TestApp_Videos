package com.awashwinter.testappvideos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerViewModel : ViewModel() {

    private val mutableCurrentVideoDuration = MutableLiveData<Long>(0)

    val liveDataCurrentVideoDuration get() : LiveData<Long> = mutableCurrentVideoDuration

    fun setCurrentDuration(duration: Long?) {
        duration?.let {
            mutableCurrentVideoDuration.value = it
        }

    }
}