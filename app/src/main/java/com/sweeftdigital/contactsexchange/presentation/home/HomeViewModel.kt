package com.sweeftdigital.contactsexchange.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel() : ViewModel() {
    val liveViewState = MutableLiveData<HomeViewState>()

    fun onPermissionResult(granted: Boolean?) {
        granted?.let {
            liveViewState.postValue(HomeViewState(it))
        }
    }
}