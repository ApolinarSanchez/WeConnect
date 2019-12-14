package com.cs5540.weconnect.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs5540.weconnect.network.Credential
import com.cs5540.weconnect.network.LoginResponse
import com.cs5540.weconnect.network.RetrofitClient
import com.cs5540.weconnect.network.WeConnectApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileViewModel () : ViewModel() {
    private val _profiles= MutableLiveData<List<Profile>>()
    private val _currentProfile= MutableLiveData<Profile>()

    val profiles : LiveData<List<Profile>>
        get() = _profiles
    val currentProfile : LiveData<Profile>
        get() = _currentProfile


    private var profileViewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        profileViewModelJob + Dispatchers.Main)
    /**
     * Sets the value of the status LiveData to the WeConnect profiless.
     */
//    fun getWeConnectProfiles(){
//        coroutineScope.launch {
//            var getProfilesDeferred = WeConnectApi.retrofitService.getProfiles()
//            try {
//                _profiles.value = getProfilesDeferred.await()
//
//            } catch (e: Exception){
//                Log.d("profileViewModel",e.toString())
//            }
//        }
//    }
//    fun getLogin(email:String,password:String){
//
//        //val answer = JSONObject("""{"name":"test name", "age":25}""")
//
//        coroutineScope.launch {
//            var getProfilesDeferred = WeConnectApi.retrofitService.login(Credential(email, password))
//            try {
//                _profiles.value = getProfilesDeferred.await()
//                Log.d("profileViewModel",_profiles.value.toString())
//            } catch (e: Exception){
//                Log.d("profileViewModelError",e.toString())
//            }
//        }
//    }

     fun loginUser(email: String,password : String) {

        RetrofitClient.instance.userLogin(email, password)
            .enqueue(object: Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    System.out.println("Failed: "+t.message);
                }
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if(!response.body()?.error!!){
                        //System.out.println("Response Success?: "+response.isSuccessful);
                        _currentProfile.value = Profile(response.body().toString());

                        System.out.println("newProfile: "+ _currentProfile.value!!.email );

                    }else{
                        System.out.println("Failed: "+response.body());

                    }

                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        profileViewModelJob.cancel()
    }
}
