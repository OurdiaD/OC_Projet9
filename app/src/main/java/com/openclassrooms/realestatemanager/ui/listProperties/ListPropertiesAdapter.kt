package com.openclassrooms.realestatemanager.ui.listProperties

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.PropertyAndPictures
import com.openclassrooms.realestatemanager.ui.detailsProperty.DetailsPropertyActivity
import com.openclassrooms.realestatemanager.ui.detailsProperty.DetailsPropertyFragment


class ListPropertiesAdapter : RecyclerView.Adapter<ListPropertiesAdapter.ListViewHolder>() {

    private lateinit var context: Context
    var properties: List<PropertyAndPictures>? = null

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
        holder.location.text = property?.property?.address?.city
        holder.type.text = context.resources.getStringArray(R.array.type_array)[property?.property?.type!!]
        holder.price.text = property.property.price.toString()

        if (property.pictures.isNotEmpty()){
            Glide.with(context).load(property.pictures[0].linkPic).into(holder.picture)
        }

        holder.itemView.setOnClickListener {
            val isTablet = context.resources.getBoolean(R.bool.isTablet)
            val activity = context as AppCompatActivity

            if (isTablet) {
                val bundle = Bundle()
                bundle.putLong("id_property", property.property.idProperty)
                val fragment = DetailsPropertyFragment()
                fragment.arguments = bundle
                val transaction = activity.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame_layout_detail, fragment)
                transaction.commit()
            } else {
                val intent = Intent(context, DetailsPropertyActivity::class.java)
                intent.putExtra("id_property", property.property.idProperty)
                startActivity(context, intent, null)
            }
        }
    }

    class ListViewHolder (itemView: View)  : RecyclerView.ViewHolder(itemView) {
        val picture: ImageView = itemView.findViewById(R.id.item_pic)
        val type: TextView = itemView.findViewById(R.id.item_type)
        val location: TextView = itemView.findViewById(R.id.item_location)
        val price: TextView = itemView.findViewById(R.id.item_price)
    }
}