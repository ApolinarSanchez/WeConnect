package com.cs5540.weconnect.ui.myprojects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cs5540.weconnect.R

class MyProjectsFragment : Fragment() {

    private lateinit var myProjectsViewModel: MyProjectsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myProjectsViewModel =
            ViewModelProviders.of(this).get(MyProjectsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_myprojects, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        myProjectsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}