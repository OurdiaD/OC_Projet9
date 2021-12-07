package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSpinners()
    }

    fun initSpinners() {
        val typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, R.layout.item_spiner)
        binding.typePropertySpinner.setAdapter(typeAdapter)

        val statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, R.layout.item_spiner)
        binding.statusPropertySpinner.setAdapter(statusAdapter)
    }

}