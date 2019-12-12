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


class  HomeFragment : Fragment() {

    /**
     * Lazily initialize our CategoryViewModel.
     */
    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProviders.of(this).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        // Give binding access to CategoryViewModel
        binding.categoryViewModel = categoryViewModel

        val categoryRecycler = binding.categoryView

        val manager : GridLayoutManager= GridLayoutManager(this.context, 2,
                                        GridLayoutManager.HORIZONTAL, false)


       categoryRecycler.layoutManager = manager



        val projectRecycler = binding.projectView
        projectRecycler.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        val projects = ArrayList<ProjectModel>()
        val paragraph = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Paulum, cum regem Persem captum adduceret, eodem flumine invectio? Itaque " +
                "hic ipse iam pridem est reiectus; Cur id non ita fit? Hoc loco tenere se Triarius " +
                "non potuit. Duo Reges: constructio interrete. Quamquam te quidem video minime esse " +
                "deterritum. Praetereo multos, in bis doctum hominem et suavem, Hieronymum, quem iam " +
                "cur Peripateticum appellem nescio. Neque solum ea communia, verum etiam paria esse " +
                "dixerunt."

        projects.add(ProjectModel("Project1",R.drawable.ic_cake,paragraph))
        projects.add(ProjectModel("Project2",R.drawable.ic_movie,paragraph))
        projects.add(ProjectModel("Project3",R.drawable.ic_android,paragraph))
        projects.add(ProjectModel("Project4",R.drawable.ic_launcher_background,paragraph))
        projects.add(ProjectModel("Project5",R.drawable.ic_launcher_background,paragraph))
        projects.add(ProjectModel("Project6",R.drawable.ic_launcher_background,paragraph))
        projects.add(ProjectModel("Project7",R.drawable.ic_launcher_background,paragraph))
        projects.add(ProjectModel("Project8",R.drawable.ic_launcher_background,paragraph))
        var adapter2 = ProjectAdapter(projects)
        projectRecycler.adapter = adapter2

        binding.categoryView.adapter = CategoryAdapter()
        // Inflate the layout for this fragment
        return binding.root
    }


}
