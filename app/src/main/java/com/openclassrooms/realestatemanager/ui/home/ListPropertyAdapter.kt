package com.openclassrooms.realestatemanager.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Property

class ListPropertyAdapter : RecyclerView.Adapter<ListPropertyAdapter.ListViewHolder>() {

    private lateinit var context: Context
    var properties: List<Property>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ListViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_property, parent, false) as View
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return properties?.size!!
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val property = properties?.get(position)
            //holder.groupNameTextView.text = group.second
        holder.location.text = property?.address?.city
        holder.type.text = context.resources.getStringArray(R.array.type_array)[property?.type!!]
        holder.price.text = property.price.toString()
    }

    class ListViewHolder (itemView: View)  : RecyclerView.ViewHolder(itemView) {
        val picture: ImageView = itemView.findViewById(R.id.item_pic)
        val type: TextView = itemView.findViewById(R.id.item_type)
        val location: TextView = itemView.findViewById(R.id.item_location)
        val price: TextView = itemView.findViewById(R.id.item_price)
    }
}