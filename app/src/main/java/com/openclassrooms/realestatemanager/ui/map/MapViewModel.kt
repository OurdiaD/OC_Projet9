package com.openclassrooms.realestatemanager.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyAndPictures

class MapViewModel(application: Application) : AndroidViewModel(application)  {

    private val propertyRepository: PropertyRepository? = PropertyRepository.getInstance(application.applicationContext)

    fun getAllProperties(): MutableLiveData<List<PropertyAndPictures>>? {
        return propertyRepository?.getList()
    }
}