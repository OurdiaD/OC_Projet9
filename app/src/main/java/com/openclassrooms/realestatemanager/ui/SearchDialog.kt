package com.openclassrooms.realestatemanager.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.databinding.DialogSearchBinding
import java.text.SimpleDateFormat
import java.util.*

class SearchDialog : DialogFragment() {

    private lateinit var binding: DialogSearchBinding
    private var condition = false

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
        var minDatein:Long = 0L
        var maxDatein:Long = 0L
        var minDatesold:Long = 0L
        var maxDatesold:Long = 0L
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
            initDate()
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun getParam(){
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
        query += getQueryLongSupTo("dateIn", minDatein)
        query += getQueryLongInfTo("dateIn", maxDatein)
        query += getQueryLongSupTo("dateSell", minDatesold)
        query += getQueryLongInfTo("dateSell", maxDatesold)

        if (condition) {
            baseQuery += " WHERE $query"
        }

        context?.let { PropertyRepository.getInstance(it)?.getQuery(baseQuery) }

    }

    private fun putLastValues() {
        binding.searchMinPriceEdit.setText(minPrice)
        binding.searchMaxPriceEdit.setText(maxPrice)
        binding.searchMinSurfaceEdit.setText(minSurface)
        binding.searchMaxSurfaceEdit.setText(maxSurface)
        binding.searchMinRoomsEdit.setText(minRoom)
        binding.searchMaxRoomsEdit.setText(maxRoom)
    }

    private fun setInterests() {
        binding.searchHealth.isChecked = health
        binding.searchSchool.isChecked = school
        binding.searchMarket.isChecked = market
        binding.searchTransports.isChecked = transports
        binding.searchRestaurant.isChecked = restaurant
        binding.searchPark.isChecked = park

    }

    private fun initDate() {
        binding.searchMinDateinEdit.setOnClickListener {
            datePicker(binding.searchMinDateinEdit)
        }
        binding.searchMaxDateinEdit.setOnClickListener {
            datePicker(binding.searchMaxDateinEdit)
        }
        binding.searchMinDatesoldEdit.setOnClickListener {
            datePicker(binding.searchMinDatesoldEdit)
        }
        binding.searchMaxDatesoldEdit.setOnClickListener {
            datePicker(binding.searchMaxDatesoldEdit)
        }
        binding.searchMinDatein.setEndIconOnClickListener {
            minDatein = 0L
            binding.searchMinDateinEdit.setText("")
        }
        binding.searchMaxDatein.setEndIconOnClickListener {
            maxDatein = 0L
            binding.searchMaxDateinEdit.setText("")
        }
        binding.searchMinDatesold.setEndIconOnClickListener {
            minDatesold = 0L
            binding.searchMinDatesoldEdit.setText("")
        }
        binding.searchMaxDatesold.setEndIconOnClickListener {
            maxDatesold = 0L
            binding.searchMaxDatesoldEdit.setText("")
        }
    }

    private fun datePicker(input: TextInputEditText) {
        val datepicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        datepicker.show(parentFragmentManager, "date")
        datepicker.addOnPositiveButtonClickListener {
            when (input.id) {
                R.id.search_min_datein_edit -> minDatein = it
                R.id.search_max_datein_edit -> maxDatein = it
                R.id.search_min_datesold_edit -> minDatesold = it
                R.id.search_max_datesold_edit -> maxDatesold = it
            }

            /*val calendar = Calendar.getInstance()
            calendar.timeInMillis = it*/
            val dateShow = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(it)
            input.setText(dateShow)
        }
    }

    private fun getQueryIntSupTo(field: String, value: String): String {
        var query = ""
        if (value != ""){
            if (condition) query = " AND"
            query = "$query $field > $value"
            condition = true
        }
        return query
    }

    private fun getQueryIntInfTo(field: String, value: String): String {
        var query = ""
        if (value != ""){
            if (condition) query = " AND"
            query = " $query $field < $value"
            condition = true
        }
        return query
    }

    private fun getQueryBool(field: String, value: Boolean): String {
        var query = ""
        if (value){
            if (condition) query = " AND"
            query = " $query $field = 1"
            condition = true
        }
        return query
    }

    private fun getQueryLongSupTo(field: String, value: Long): String {
        var query = ""
        if (value != 0L){
            if (condition) query = " AND"
            query = "$query $field > $value"
            condition = true
        }
        return query
    }

    private fun getQueryLongInfTo(field: String, value: Long): String {
        var query = ""
        if (value != 0L){
            if (condition) query = " AND"
            query = " $query $field < $value"
            condition = true
        }
        return query
    }
}