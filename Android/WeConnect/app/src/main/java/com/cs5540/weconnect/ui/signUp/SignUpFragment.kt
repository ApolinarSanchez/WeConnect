package com.cs5540.weconnect.ui.signUp

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
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val signUpViewModel: SignUpViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sign_up_button.setOnClickListener {
            signUpViewModel.signUp()
//            handleSend { signUpViewModel.sendPushNotification() }

            hideKeyboard()
        }

        go_to_login.setOnClickListener {
            findNavController().navigate(R.id.nav_login)
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

//    private fun handleSend(toSend: () -> Unit) {
//        if (signUpViewModel.user) {
//            toSend()
//            findNavController().navigate(R.id.nav_home)
//        } else {
//            Toast.makeText(activity?.applicationContext, "Network error", Toast.LENGTH_SHORT).show()
//        }
//        hideKeyboard()
//    }
}