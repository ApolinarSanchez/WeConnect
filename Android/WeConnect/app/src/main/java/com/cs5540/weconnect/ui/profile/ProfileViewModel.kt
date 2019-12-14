package com.cs5540.weconnect.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs5540.weconnect.network.Credential
import com.cs5540.weconnect.network.WeConnectApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel () : ViewModel() {
    private val _profiles= MutableLiveData<List<Profile>>()
    val profiles : LiveData<List<Profile>>
        get() = _profiles

    private var profileViewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        profileViewModelJob + Dispatchers.Main)
    /**
     * Sets the value of the status LiveData to the WeConnect profiless.
     */
    fun getWeConnectProfiles(){
        coroutineScope.launch {
            var getProfilesDeferred = WeConnectApi.retrofitService.getProfiles()
            try {
                _profiles.value = getProfilesDeferred.await()

            } catch (e: Exception){
                Log.d("profileViewModel",e.toString())
            }
        }
    }
    fun getLogin(email:String,password:String){
        coroutineScope.launch {
            var getProfilesDeferred = WeConnectApi.retrofitService.login(Credential(email,password))
            try {
                _profiles.value = getProfilesDeferred.await()
                Log.d("profileViewModel",_profiles.value.toString())
            } catch (e: Exception){
                Log.d("profileViewModelError",e.toString())
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        profileViewModelJob.cancel()
    }
}
