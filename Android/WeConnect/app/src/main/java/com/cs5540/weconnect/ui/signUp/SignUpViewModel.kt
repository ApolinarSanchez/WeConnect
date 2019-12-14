package com.cs5540.weconnect.ui.signUp

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs5540.weconnect.util.NotificationHelper
import java.util.*

class SignUpViewModel(val app: Application) : AndroidViewModel(app) {

    var loading = ObservableField(View.GONE)

    var email = ObservableField("")
    var password = ObservableField("")

//    private val gson by lazy { Gson() }
    private val notificationHelper by lazy { NotificationHelper(app) }
//    private val sharedPreferenceHelper by lazy { SharedPreferenceHelper(app) }
//    private val letterRepository by lazy { LetterRepository(app) }

//    val user: User? = null

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    fun signUp() {
        loading.set(1)
//        user = makeACallToGetUser(email, password)
        loading.set(0)
    }

    fun sendPushNotification() {
//        notificationHelper.sendLocalNotification(user)
    }
}