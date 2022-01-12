package com.openclassrooms.realestatemanager.ui

import android.app.Dialog
import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.databinding.DialogSearchBinding

class SearchDialog : DialogFragment() {

    private lateinit var binding: DialogSearchBinding
    var condition = false

    companion object {
        var minPrice: String? = null
        var maxPrice: String? = null
        var minSurface: String? = null
        var maxSurface: String? = null
        var minRoom: String? = null
        var maxRoom: String? = null
        var health: Boolean = false
        var school: Boolean = false
        var market: Boolean = false
        var park: Boolean = false
        var restaurant: Boolean = false
        var transports: Boolean = false
    }

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
            putLastValues()
            setInterests()
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun getParam(){
        minPrice = binding.searchMinPriceEdit.text.toString()
        maxPrice = binding.searchMaxPriceEdit.text.toString()
        minSurface = binding.searchMinSurfaceEdit.text.toString()
        maxSurface = binding.searchMaxSurfaceEdit.text.toString()
        minRoom = binding.searchMinRoomsEdit.text.toString()
        maxRoom = binding.searchMaxRoomsEdit.text.toString()
        health = binding.searchHealth.isChecked
        school = binding.searchSchool.isChecked
        market = binding.searchMarket.isChecked
        transports = binding.searchTransports.isChecked
        restaurant = binding.searchRestaurant.isChecked
        park = binding.searchPark.isChecked

        var query = ""
        var baseQuery = "SELECT * FROM property"
        query += getQueryIntSupTo("price", minPrice!!)
        query += getQueryIntInfTo("price", maxPrice!!)
        query += getQueryIntSupTo("surfaceArea", minSurface!!)
        query += getQueryIntSupTo("surfaceArea", maxSurface!!)
        query += getQueryIntSupTo("numberOfRooms", minRoom!!)
        query += getQueryIntInfTo("numberOfRooms", maxRoom!!)
        query += getQueryBool("health", health)
        query += getQueryBool("school", school)
        query += getQueryBool("market", market)
        query += getQueryBool("transports", transports)
        query += getQueryBool("restaurant", restaurant)
        query += getQueryBool("park", park)

        if (condition) {
            baseQuery += " WHERE $query"
        }

        context?.let { PropertyRepository.getInstance(it)?.getQuery(baseQuery) }

    }

    fun getQueryIntSupTo(field: String, value: String): String {
        var query = ""
        if (value != ""){
            if (condition) query = " AND"
            query = "$query $field > $value"
            condition = true
        }
        return query
    }

    fun getQueryIntInfTo(field: String, value: String): String {
        var query = ""
        if (value != ""){
            if (condition) query = " AND"
            query = " $query $field < $value"
            condition = true
        }
        return query
    }

    fun getQueryBool(field: String, value: Boolean): String {
        var query = ""
        if (value){
            if (condition) query = " AND"
            query = " $query $field = 1"
            condition = true
        }
        return query
    }

    fun putLastValues() {
        binding.searchMinPriceEdit.setText(minPrice)
        binding.searchMaxPriceEdit.setText(maxPrice)
        binding.searchMinSurfaceEdit.setText(minSurface)
        binding.searchMaxSurfaceEdit.setText(maxSurface)
        binding.searchMinRoomsEdit.setText(minRoom)
        binding.searchMaxRoomsEdit.setText(maxRoom)
    }

    fun setInterests() {
        binding.searchHealth.isChecked = health
        binding.searchSchool.isChecked = school
        binding.searchMarket.isChecked = market
        binding.searchTransports.isChecked = transports
        binding.searchRestaurant.isChecked = restaurant
        binding.searchPark.isChecked = park

    }


}