package com.example.android.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.android.navigation.databinding.FragmentHomeBinding
/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home, container, false)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
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
                    binding.searchView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                return true
            }
        })

        return binding.root
    }

}