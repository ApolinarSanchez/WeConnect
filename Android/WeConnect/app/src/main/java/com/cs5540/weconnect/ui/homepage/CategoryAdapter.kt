package com.cs5540.weconnect.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.cs5540.weconnect.databinding.CategoryItemLayoutBinding
class CategoryAdapter : ListAdapter<Category, CategoryAdapter.ViewHolder>(DiffCallback) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

   class ViewHolder(private var binding: CategoryItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category) {
            binding.category = category
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.categoryId == newItem.categoryId
        }

    }
}
