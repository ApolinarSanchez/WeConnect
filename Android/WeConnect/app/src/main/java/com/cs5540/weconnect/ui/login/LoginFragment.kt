package com.cs5540.weconnect.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels

import com.cs5540.weconnect.R
import com.cs5540.weconnect.util.hideKeyboard
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("y", "y")

        login_button.setOnClickListener {
             loginViewModel.login().also {
                 handleSend { loginViewModel.sendPushNotification() }
             }


            hideKeyboard()
        }
    }

    private fun handleSend(toSend: () -> Unit) {
        if (loginViewModel.currentProfile.value != null) {
            toSend()
            findNavController().navigate(R.id.nav_home)
        } else {
            Toast.makeText(activity?.applicationContext, "Incorrect email or password", Toast.LENGTH_SHORT).show()
        }
        hideKeyboard()
    }
}
