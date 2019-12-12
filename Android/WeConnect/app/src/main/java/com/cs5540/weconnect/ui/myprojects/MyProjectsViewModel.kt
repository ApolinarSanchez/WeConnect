package com.cs5540.weconnect.ui.myprojects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyProjectsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is My Projects Fragment"
    }
    val text: LiveData<String> = _text
}