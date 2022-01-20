package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyAndPictures
import java.util.concurrent.Executors

class PropertyRepository(context: Context) {
    private var propertyDao: PropertyDao
    private var pictureDao: PictureDao
    private var listProperties: MutableLiveData<List<PropertyAndPictures>>? = null

    companion object {
        private var instance: PropertyRepository? = null
        fun getInstance(context: Context): PropertyRepository? {
            if (instance == null) {
                instance = PropertyRepository(context)
            }
            return instance
        }
    }

    init {
        val db: REMDatabase = REMDatabase.getInstance(context)!!
        propertyDao = db.propertyDao()
        pictureDao = db.pictureDao()
    }

    fun getList(): MutableLiveData<List<PropertyAndPictures>>? {
        if (listProperties == null) {
            listProperties = MediatorLiveData()
            getAllWithPictures()
        }
        return listProperties
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

    fun getAllWithPictures() {
        Executors.newSingleThreadExecutor().execute {
            listProperties?.postValue(propertyDao.getAllWithPictures())
        }
    }

    fun getQuery(query: String){
        Executors.newSingleThreadExecutor().execute {
            listProperties?.postValue(propertyDao.getQuery(SimpleSQLiteQuery(query)))
        }
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

    fun deletePicture(id: Long, path: String){
        Executors.newSingleThreadExecutor().execute {
            pictureDao.deletePic(id, path)
        }
    }
}