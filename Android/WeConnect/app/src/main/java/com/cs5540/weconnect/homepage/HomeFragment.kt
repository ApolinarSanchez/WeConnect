package com.cs5540.weconnect.homepage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.cs5540.weconnect.R
import com.cs5540.weconnect.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class  HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home, container, false)

        val rv = binding.categoryView
        rv.layoutManager = GridLayoutManager(this.context,4)
        val cats = ArrayList<CategoryModel>()
        cats.add(CategoryModel("JAVA",R.drawable.java))
        cats.add(CategoryModel("KOTLIN",R.drawable.kotlin))
        cats.add(CategoryModel("Python",R.drawable.java))
        cats.add(CategoryModel("C++",R.drawable.java))
        cats.add(CategoryModel("C",R.drawable.kotlin))
        cats.add(CategoryModel("RUBY",R.drawable.kotlin))
        cats.add(CategoryModel("C#",R.drawable.kotlin))
        cats.add(CategoryModel("HTML",R.drawable.kotlin))
        cats.add(CategoryModel("JAVA",R.drawable.java))
        cats.add(CategoryModel("KOTLIN",R.drawable.kotlin))
        cats.add(CategoryModel("Python",R.drawable.java))
        cats.add(CategoryModel("C++",R.drawable.java))
        cats.add(CategoryModel("C",R.drawable.kotlin))
        cats.add(CategoryModel("RUBY",R.drawable.kotlin))
        cats.add(CategoryModel("C#",R.drawable.kotlin))
        cats.add(CategoryModel("HTML",R.drawable.kotlin))

        var adapter = CategoryAdapter(cats)
        rv.adapter = adapter

//        val rv2 = binding.projectView
//        rv2.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
//        val projects = ArrayList<ProjectModel>()
//        val paragraph = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
//                " Paulum, cum regem Persem captum adduceret, eodem flumine invectio? Itaque " +
//                "hic ipse iam pridem est reiectus; Cur id non ita fit? Hoc loco tenere se Triarius " +
//                "non potuit. Duo Reges: constructio interrete. Quamquam te quidem video minime esse " +
//                "deterritum. Praetereo multos, in bis doctum hominem et suavem, Hieronymum, quem iam " +
//                "cur Peripateticum appellem nescio. Neque solum ea communia, verum etiam paria esse " +
//                "dixerunt."
//        projects.add(ProjectModel("Project1","@drawable/java.png",paragraph))
//        projects.add(ProjectModel("Project2","@drawable/java.png",paragraph))
//        var adapter2 = ProjectAdaptor(projects)
//        rv2.adapter = adapter2


        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            //as the new character is added(can be used to generate suggestions)
            override fun onQueryTextChange(newText: String): Boolean {
                Toast.makeText(activity,newText,Toast.LENGTH_SHORT).show()
                return true
            }
            //click  submit
            override fun onQueryTextSubmit(query: String): Boolean {
                //TO DO navigate to the result page
                Toast.makeText(activity,query,Toast.LENGTH_LONG).show()

                // Hide the keyboard.
                val inputMethodManager =
                    binding.searchBar.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                return true
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }


}
