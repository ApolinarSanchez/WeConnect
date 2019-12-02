package com.cs5540.weconnect.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cs5540.weconnect.R

import android.view.View
import android.widget.ImageView
import android.widget.TextView

class CategoryAdapter(val catList: ArrayList<CategoryModel>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name?.text = catList[position].name
        holder.img?.setImageResource(catList[position].imgId)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item_layout, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.category_name)
        val img = itemView.findViewById<ImageView>(R.id.category_image)
    }

}
