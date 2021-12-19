package com.openclassrooms.realestatemanager.ui.listProperties

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.PropertyAndPictures

class ListPropertiesModel(application: Application) : AndroidViewModel(application) {

    private val propertyRepository: PropertyRepository = PropertyRepository(application)


    fun getAllProperties(): LiveData<List<PropertyAndPictures>> {
        return propertyRepository.getAllWithPictures()
    }
}