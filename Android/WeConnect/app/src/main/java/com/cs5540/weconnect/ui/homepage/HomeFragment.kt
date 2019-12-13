package com.cs5540.weconnect.ui.homepage

import android.os.Bundle
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

        categoryViewModel.navigateToProjects.observe(this, Observer {category ->
            category?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionNavHomeToNavProject(category))
                categoryViewModel.onProjectsNavigated()
            }
        })
        val manager : GridLayoutManager= GridLayoutManager(this.context, 2,
                                        GridLayoutManager.HORIZONTAL, false)

       categoryRecycler.layoutManager = manager


        categoryRecycler.layoutManager = manager

        // Inflate the layout for this fragment
        // Give binding access to ProjectViewModel
        binding.projectViewModel = projectViewModel

        val projectRecycler = binding.projectView


        val manager2: LinearLayoutManager =
            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)

        projectRecycler.layoutManager = manager2


    binding.categoryView.adapter = CategoryAdapter(CategoryAdapter.CategoryListener { categoryId->
//            Toast.makeText(context, "${categoryId}", Toast.LENGTH_LONG).show()
        categoryViewModel.onCategoryClicked(categoryId)
    })
        binding.projectView.adapter = ProjectAdapter()


        return binding.root
    }
}