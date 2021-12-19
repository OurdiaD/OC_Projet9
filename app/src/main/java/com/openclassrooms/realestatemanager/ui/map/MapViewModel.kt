package com.openclassrooms.realestatemanager.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyAndPictures

class MapViewModel(application: Application) : AndroidViewModel(application)  {

    val propertyRepository: PropertyRepository = PropertyRepository(application)

    fun getAllProperties(): LiveData<List<Property>> {
        return propertyRepository.getAll()
    }
}