package com.cs5540.weconnect.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cs5540.weconnect.R

import android.view.View
import android.widget.ImageView
import android.widget.TextView

class ProjectAdapter(val catList: ArrayList<Project>): RecyclerView.Adapter<ProjectAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name?.text = catList[position].name
        holder.img?.setImageResource(catList[position].imgId)
        holder.description?.text=(catList[position].description)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.project_item_layout, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return catList.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.project_name)
        val description = itemView.findViewById<TextView>(R.id.project_description)
        val img = itemView.findViewById<ImageView>(R.id.project_image)
    }

}
