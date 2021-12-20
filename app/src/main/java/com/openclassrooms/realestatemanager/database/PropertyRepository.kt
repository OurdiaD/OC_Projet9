package com.openclassrooms.realestatemanager.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyAndPictures
import java.util.concurrent.Executors

class PropertyRepository(context: Context) {
    private var propertyDao: PropertyDao
    private var pictureDao: PictureDao

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

    fun getAll(): LiveData<List<Property>> {
        return propertyDao.getAll()
    }

    fun getAllWithPictures(): LiveData<List<PropertyAndPictures>> {
        return propertyDao.getAllWithPictures()
    }

    fun getOne(idProperty: Long): LiveData<PropertyAndPictures> {
        return propertyDao.getOne(idProperty)
    }

    fun updateProperty(property: Property, pictures: List<String>) {
        Executors.newSingleThreadExecutor().execute {
            propertyDao.update(property)
            for (pic in pictures){
                pictureDao.insertPic(Picture(property.idProperty, pic))
            }
        }
    }
}