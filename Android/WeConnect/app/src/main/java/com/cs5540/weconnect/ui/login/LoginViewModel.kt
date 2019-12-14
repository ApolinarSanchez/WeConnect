package com.cs5540.weconnect.ui.login

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.navGraphViewModels
import com.cs5540.weconnect.R
import com.cs5540.weconnect.network.LoginResponse
import com.cs5540.weconnect.network.RetrofitClient
import com.cs5540.weconnect.ui.profile.Profile
import com.cs5540.weconnect.ui.profile.ProfileViewModel
import com.cs5540.weconnect.util.NotificationHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginViewModel(val app: Application) : AndroidViewModel(app) {
    var loading = ObservableField(1)

    private val _currentProfile= MutableLiveData<Profile>()
    val currentProfile : LiveData<Profile>
        get() = _currentProfile

    var email = ObservableField("")
    var password = ObservableField("")

    //    private val gson by lazy { Gson() }
    private val notificationHelper by lazy { NotificationHelper(app) }
//    private val sharedPreferenceHelper by lazy { SharedPreferenceHelper(app) }
//    private val letterRepository by lazy { LetterRepository(app) }

//    var user: LiveData<Profile> = g

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    fun login() {
        Log.d("email", "Email it ${email.get()}")
        Log.d("passworrd", "password is ${password.get()}")
        loading.set(0)
        loginUser(email.get()!!, password.get()!!)
        loading.set(1)
    }

    fun sendPushNotification() {
        notificationHelper.sendLocalNotification(currentProfile.value!!)
    }

    private fun loginUser(email: String,password : String) {
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
}