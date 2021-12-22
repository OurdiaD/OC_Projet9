package com.openclassrooms.realestatemanager.ui.editProperty

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyAndPictures

class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val propertyRepository: PropertyRepository = PropertyRepository(application)

    fun getOneProperty(idProperty: Long): LiveData<PropertyAndPictures> {
        return propertyRepository.getOne(idProperty)
    }

    fun addProperty(property: Property, pictures: List<String>){
        propertyRepository.addProperty(property, pictures)
    }

    fun updateProperty(property: Property, pictures: List<String>){
        propertyRepository.updateProperty(property, pictures)
    }

    fun deletePicture(id: Long, path: String){
        propertyRepository.deletePicture(id, path)
    }
}