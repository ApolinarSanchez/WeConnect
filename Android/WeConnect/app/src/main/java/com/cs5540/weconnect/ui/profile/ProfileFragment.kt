package com.cs5540.weconnect.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs5540.weconnect.R
import com.cs5540.weconnect.databinding.FragmentHomeBinding
import com.cs5540.weconnect.databinding.FragmentProfileBinding


class  ProfileFragment: Fragment(){
    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater, R.layout.fragment_profile, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        profileViewModel.getWeConnectProfiles()
        //profileViewModel.getLogin("john1@gmail.com","123456")
        // Inflate the layout for this fragment
        // Give binding access to ProfileViewModel
        binding.profileViewModel = profileViewModel


        val profileRecycler = binding.profileView

        val manager2: LinearLayoutManager =
            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)

        profileRecycler.layoutManager = manager2

        binding.profileView.adapter = ProfileAdapter()

        return binding.root
    }
}