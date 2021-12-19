package com.openclassrooms.realestatemanager.ui.detailsProperty

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.PropertyAndPictures

class DetailsViewModel(application: Application) : AndroidViewModel(application)  {
    private val propertyRepository: PropertyRepository = PropertyRepository(application)

    fun getOneProperty(idProperty: Long): LiveData<PropertyAndPictures> {
        return propertyRepository.getOne(idProperty)
    }
}
