package com.example.ebookapp.dummy

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PermissionViewModel : ViewModel() {

    private  val _showDialog = MutableStateFlow(false)
    val showDialog = _showDialog.asStateFlow()

    private  val _launchAppSettings = MutableStateFlow(false)
    val launchAppSettings = _launchAppSettings.asStateFlow()

    fun updateShowDialog(show: Boolean){
        _showDialog.update { show }
    }
    fun updateLaunchAppSettings(launch: Boolean){
        _launchAppSettings.update { launch }
    }
}