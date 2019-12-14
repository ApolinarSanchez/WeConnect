package com.cs5540.weconnect.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.cs5540.weconnect.R
import com.cs5540.weconnect.util.hideKeyboard
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            loginViewModel.login()
            handleSend { loginViewModel.sendPushNotification() }

            hideKeyboard()
        }

//        val letter = Gson().fromJson(
//            args.letter.urlDecode(),
//            Letter::class.java
//        )
//        lettersViewModel.saveLetterToInbox(letter)
//
//        viewPager.adapter = context?.let {
//            LetterPagerAdapter(
//                it, letter
//            )
//        }
    }

    private fun handleSend(toSend: () -> Unit) {
        if (loginViewModel.user) {
            toSend()
            findNavController().navigate(R.id.nav_home)
        } else {
            Toast.makeText(activity?.applicationContext, "Incorrect email or password", Toast.LENGTH_SHORT).show()
        }
        hideKeyboard()
    }
}