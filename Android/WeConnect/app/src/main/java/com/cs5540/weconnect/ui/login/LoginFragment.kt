package com.cs5540.weconnect.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels

import com.cs5540.weconnect.R
import com.cs5540.weconnect.databinding.FragmentLoginBinding
import com.cs5540.weconnect.ui.profile.Profile
import com.cs5540.weconnect.util.hideKeyboard
import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )
        binding.viewModel = loginViewModel

        container?.rootView?.fab?.hide()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("y", "y")

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        login_button.setOnClickListener {
            Log.d("email", "Email it ${loginViewModel.email.get()!!}")
            Log.d("passworrd", "password is ${loginViewModel.password.get()!!}")
            if (loginViewModel.email.get() != null) {
                loginViewModel.login()
            }

            hideKeyboard()
        }

        loginViewModel.currentProfile.observe(this, Observer<Profile> {
            handleSend { loginViewModel.sendPushNotification() }
        })
    }

    private fun handleSend(toSend: () -> Unit) {
//        if (loginViewModel.currentProfile != null) {
            toSend()
            findNavController().navigate(R.id.nav_home)
//        } else {
//            Toast.makeText(activity?.applicationContext, "Incorrect email or password", Toast.LENGTH_SHORT).show()
//        }
    }
}
