package com.sweeftdigital.contactsexchange.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    private val _permissionState = MutableLiveData<MainViewState>()
    val permissionState: LiveData<MainViewState>
        get() = _permissionState

    fun onPermissionResult(granted: Boolean?) {
        granted?.let {
            _permissionState.postValue(MainViewState(it))
        }
    }
}