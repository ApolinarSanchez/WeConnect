package com.cs5540.weconnect.ui.homepage

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
import com.cs5540.weconnect.databinding.FragmentHomeBinding
import com.cs5540.weconnect.ui.projects.ProjectViewModel


class  HomeFragment : Fragment() {

    /**
     * Lazily initialize our CategoryViewModel.
     */
    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProviders.of(this).get(CategoryViewModel::class.java)
    }
    private val projectViewModel: ProjectViewModel by lazy {
        ViewModelProviders.of(this).get(ProjectViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home, container, false
        )

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        // Give binding access to CategoryViewModel
        binding.categoryViewModel = categoryViewModel

        val categoryRecycler = binding.categoryView

        val manager: GridLayoutManager = GridLayoutManager(
            this.context, 2,
            GridLayoutManager.HORIZONTAL, false
        )

        categoryRecycler.layoutManager = manager
        binding.categoryView.adapter = CategoryAdapter()
////////////////////

        // Inflate the layout for this fragment
        // Give binding access to CategoryViewModel
        binding.projectViewModel = projectViewModel

        val projectRecycler = binding.projectView

        val manager2: GridLayoutManager = GridLayoutManager(
            this.context, 2,
            GridLayoutManager.HORIZONTAL, false
        )

        projectRecycler.layoutManager = manager2


        binding.projectView.adapter = ProjectAdapter()

        return binding.root
    }
}