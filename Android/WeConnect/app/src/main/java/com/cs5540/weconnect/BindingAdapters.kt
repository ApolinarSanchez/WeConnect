package com.cs5540.weconnect

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cs5540.weconnect.ui.homepage.Category
import com.cs5540.weconnect.ui.homepage.CategoryAdapter
import com.cs5540.weconnect.ui.projects.Project
import com.cs5540.weconnect.ui.homepage.ProjectAdapter

@BindingAdapter("categoryData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Category>?) {
    val adapter = recyclerView.adapter as? CategoryAdapter
    adapter?.submitList(data)
}
@BindingAdapter("projectData")
fun bindRecyclerView2(recyclerView: RecyclerView, data: List<Project>?) {
    val adapter = recyclerView.adapter as? ProjectAdapter
    adapter?.submitList(data)
}
