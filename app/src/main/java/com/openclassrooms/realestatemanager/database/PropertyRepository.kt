package com.openclassrooms.realestatemanager.database

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.REMDatabase
import com.openclassrooms.realestatemanager.model.Property
import java.util.concurrent.Executors

class PropertyRepository(context: Context) {
    var propertyDao: PropertyDao

    init {
        val db: REMDatabase = REMDatabase.getInstance(context)!!
        propertyDao = db.propertyDao()
    }

    fun addProperty(property: Property) {
        Executors.newSingleThreadExecutor().execute { propertyDao.insert(property) }
    }

    fun getAll(): LiveData<List<Property>> {
        return propertyDao.getAll()
    }

    fun getOne(idProperty: Long): LiveData<Property> {
        return propertyDao.getOne(idProperty)
    }
}