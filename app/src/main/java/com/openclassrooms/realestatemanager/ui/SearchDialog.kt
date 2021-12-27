package com.openclassrooms.realestatemanager.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.databinding.DialogSearchBinding

class SearchDialog : DialogFragment() {

    private lateinit var binding: DialogSearchBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSearchBinding.inflate(layoutInflater)
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
                .setTitle(R.string.action_search)
                .setPositiveButton(R.string.action_search
                ) { dialog, id ->
                    getParam()
                }
                .setNegativeButton(
                    R.string.cancel) { dialog, id ->
                    this.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun getParam(){
        //val binding = DialogSearchBinding.inflate(layoutInflater)
        val minPrice = binding.searchMinPriceEdit.text.toString()
        val maxPrice = binding.searchMaxPriceEdit.text.toString()
        val minSurface = binding.searchMinSurfaceEdit.text.toString()
        val maxSurface = binding.searchMaxSurfaceEdit.text.toString()
        val minRoom = binding.searchMinRoomsEdit.text.toString()
        val maxRoom = binding.searchMaxRoomsEdit.text.toString()

        Log.d("lol dialog", "minPrice")
        Log.d("lol dialog", minPrice)
        Log.d("lol dialog", "" +binding)
        Log.d("lol dialog", "" +binding.searchMaxPriceEdit)
        Log.d("lol dialog", "" +binding.searchMaxPriceEdit.text)
        Log.d("lol dialog", "" +binding.searchMaxPriceEdit.text.toString())
        Log.d("lol dialog", minSurface)

        var query = ""
        var baseQuery = "SELECT * FROM property"
        var condition = false

        if(minPrice != "") {
            query += getQueryIntSupTo("price", minPrice, condition)
            condition = true
        }

        if(maxPrice != "") {
            query += getQueryIntInfTo("price", maxPrice,condition)
            condition = true
        }

        if (minSurface != "") {
            query += getQueryIntSupTo("surfaceArea", minSurface, condition)
            condition = true
        }

        if (maxSurface != "") {
            query += getQueryIntSupTo("surfaceArea", maxSurface, condition)
            condition = true
        }

        if (minRoom != "") {
            query += getQueryIntSupTo("numberOfRooms", minRoom, condition)
            condition = true
        }

        if (maxRoom != "") {
            query += getQueryIntInfTo("numberOfRooms", maxRoom, condition)
            condition = true
        }

        if (condition) {
            baseQuery += " WHERE $query"
        }

        context?.let { PropertyRepository.getInstance(it)?.getQuery(baseQuery) }

    }

    fun getQueryIntSupTo(field: String, value: String, condition: Boolean): String {
        var query = ""
        if (condition) query = " AND"
        query = "$query $field > $value"
        return query
    }

    fun getQueryIntInfTo(field: String, value: String, condition: Boolean): String {
        var query = ""
        if (condition) query = " AND"
        query = " $query $field < $value"
        return query
    }

}