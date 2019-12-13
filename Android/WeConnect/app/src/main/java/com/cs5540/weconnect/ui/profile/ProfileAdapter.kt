package com.cs5540.weconnect.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.cs5540.weconnect.databinding.ProfileItemLayoutBinding

class ProfileAdapter : ListAdapter<Profile, ProfileAdapter.ViewHolder>(DiffCallback) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile = getItem(position)
        holder.bind(profile)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ProfileItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    class ViewHolder(private var binding: ProfileItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(profile: Profile) {
            binding.profile = profile
            binding.executePendingBindings()
        }
    }
    companion object DiffCallback : DiffUtil.ItemCallback<Profile>() {
        override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem===newItem
        }
        override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem.profileId == newItem.profileId
        }

    }
}
