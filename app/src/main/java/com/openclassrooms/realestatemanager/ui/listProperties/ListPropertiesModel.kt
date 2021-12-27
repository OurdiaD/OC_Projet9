package com.openclassrooms.realestatemanager.ui.listProperties

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.model.PropertyAndPictures

class ListPropertiesModel(application: Application) : AndroidViewModel(application) {

    private val propertyRepository: PropertyRepository? = PropertyRepository.getInstance(application.applicationContext)


    fun getAllProperties(): MutableLiveData<List<PropertyAndPictures>>? {
        return propertyRepository?.getList()
    }
}