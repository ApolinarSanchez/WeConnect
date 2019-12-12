package com.cs5540.weconnect.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.cs5540.weconnect.databinding.ProjectItemLayoutBinding
import com.cs5540.weconnect.ui.projects.Project

class ProjectAdapter : ListAdapter<Project, ProjectAdapter.ViewHolder>(DiffCallback) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = getItem(position)
        holder.bind(project)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ProjectItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    class ViewHolder(private var binding: ProjectItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(project: Project) {
            binding.project = project
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.projectId == newItem.projectId
        }

    }
}
