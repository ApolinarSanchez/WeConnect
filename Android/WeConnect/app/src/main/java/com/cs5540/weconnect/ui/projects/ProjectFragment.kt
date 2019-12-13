package com.cs5540.weconnect.ui.projects


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.cs5540.weconnect.R
import com.cs5540.weconnect.databinding.FragmentProjectBinding

/**
 * A simple [Fragment] subclass.
 */
class ProjectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProjectBinding>(
            inflater,
            R.layout.fragment_project, container, false)
        val arguments = arguments?.let { ProjectFragmentArgs.fromBundle(it) }
        val categoryId = arguments?.categoryId

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false)
    }


}
