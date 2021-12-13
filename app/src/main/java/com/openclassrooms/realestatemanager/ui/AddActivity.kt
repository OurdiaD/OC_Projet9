package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding
import com.openclassrooms.realestatemanager.model.Property

class AddActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)

        //initSpinners()
        setContentView(binding.root)

        binding.addProperty.setOnClickListener {
            saveData()
            Log.d("lol add", "click")
        }
    }

    private fun initSpinners() {
        val typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, R.layout.item_spiner)
        binding.typePropertySpinner.setAdapter(typeAdapter)
        binding.typePropertySpinner.setSelection(0)

        val statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, R.layout.item_spiner)
        binding.statusPropertySpinner.setAdapter(statusAdapter)
        binding.statusPropertySpinner.setSelection(0)
    }

    private fun saveData() {
        val type =  binding.typePropertySpinner.selectedItemPosition
        val status = binding.statusPropertySpinner.selectedItemPosition
        val price  = Integer.parseInt(binding.pricePropertyEdit.text.toString())
        val surface = Integer.parseInt(binding.surfacePropertyEdit.text.toString())
        val rooms = Integer.parseInt(binding.roomsPropertyEdit.text.toString())
        val location = binding.locationPropertyEdit.text.toString()
        val describe = binding.describePropertyEdit.text.toString()

        val newProperty = Property(type, status, surface)
        newProperty.price = price
        newProperty.numberOfRooms = rooms
        newProperty.location = location
        newProperty.describe = describe

        val repo = PropertyRepository(application)
        repo.addProperty(newProperty)
    }

}