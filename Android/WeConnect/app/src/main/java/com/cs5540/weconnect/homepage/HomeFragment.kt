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
        rv.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        val cats = ArrayList<CategoryModel>()
        cats.add(CategoryModel("JAVA"))
        cats.add(CategoryModel("PYTHON"))
        cats.add(CategoryModel("JAVA"))
        cats.add(CategoryModel("JAVASCRIPT"))

        var adapter = CategoryAdapter(cats)
        rv.adapter = adapter


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
