package com.cs5540.weconnect.ui.projects

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs5540.weconnect.network.WeConnectApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProjectViewModel : ViewModel() {
    private val _projects= MutableLiveData<List<Project>>()
    val projects : LiveData<List<Project>>
        get() = _projects
    private var projectViewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        projectViewModelJob + Dispatchers.Main)
    init {
        getWeConnectProjects()
    }
    /**
     * Sets the value of the status LiveData to the WeConnect projects.
     */
    private fun getWeConnectProjects(){
        coroutineScope.launch {
            var getProjectsDeferred = WeConnectApi.retrofitService.getProjects()
            try {
                _projects.value = getProjectsDeferred.await()

            } catch (e: Exception) {
            }
        }
    }
    public fun getWeConnectProjectsInCategory(categoryId:String){
        coroutineScope.launch {
            var getProjectsDeferred = WeConnectApi.retrofitService.getProjectsByCategory(categoryId)
            try {
                _projects.value = getProjectsDeferred.await()
                Log.d("ProjectViewModel",_projects.value.toString())
            } catch (e: Exception) {
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        projectViewModelJob.cancel()
    }
}
