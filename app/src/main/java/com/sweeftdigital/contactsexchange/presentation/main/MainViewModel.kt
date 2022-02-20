package com.sweeftdigital.contactsexchange.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    val liveViewState = MutableLiveData<MainViewState>()

    fun onPermissionResult(granted: Boolean?) {
        granted?.let {
            liveViewState.postValue(MainViewState(it))
        }
    }
}