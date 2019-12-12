package com.cs5540.weconnect.ui.projects

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs5540.weconnect.R
import com.cs5540.weconnect.databinding.FragmentProjectBinding
import com.cs5540.weconnect.ui.homepage.ProjectAdapter
class  ProjectFragment : Fragment(){

    /**
     * Lazily initialize our CategoryViewModel.
     */
    private val projectViewModel: ProjectViewModel by lazy {
        ViewModelProviders.of(this).get(ProjectViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentProjectBinding>(
            inflater,
            R.layout.fragment_project, container, false
        )

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        // Inflate the layout for this fragment
        // Give binding access to ProjectViewModel
        binding.projectViewModel = projectViewModel

        val projectRecycler = binding.projectView

        val manager2: LinearLayoutManager =
            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)

        projectRecycler.layoutManager = manager2

        binding.projectView.adapter = ProjectAdapter()

        return binding.root
    }
}