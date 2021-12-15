package com.openclassrooms.realestatemanager.database

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.REMDatabase
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyAndPictures
import java.util.concurrent.Executors

class PropertyRepository(context: Context) {
    var propertyDao: PropertyDao
    var pictureDao: PictureDao

    init {
        val db: REMDatabase = REMDatabase.getInstance(context)!!
        propertyDao = db.propertyDao()
        pictureDao = db.pictureDao()
    }

    fun addProperty(property: Property, pictures: List<String>) {
        Executors.newSingleThreadExecutor().execute {
            val id = propertyDao.insert(property)
            for (pic in pictures){
                pictureDao.insertPic(Picture(id, pic))
            }
        }
    }

    fun getAll(): LiveData<List<PropertyAndPictures>> {
        return propertyDao.getAll()
    }

    fun getOne(idProperty: Long): LiveData<PropertyAndPictures> {
        return propertyDao.getOne(idProperty)
    }
}