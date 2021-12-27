package com.openclassrooms.realestatemanager.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.Property

class MapViewModel(application: Application) : AndroidViewModel(application)  {

    private val propertyRepository: PropertyRepository? = PropertyRepository.getInstance(application.applicationContext)

    fun getAllProperties(): LiveData<List<Property>> {
        return propertyRepository!!.getAll()
    }
}