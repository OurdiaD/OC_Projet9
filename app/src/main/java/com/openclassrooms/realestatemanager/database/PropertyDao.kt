package com.openclassrooms.realestatemanager.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyAndPictures

@Dao
interface PropertyDao {

    @Transaction
    @Query("SELECT * FROM property")
    fun getAllWithPictures(): LiveData<List<PropertyAndPictures>>

    @Query("SELECT * FROM property")
    fun getAll(): LiveData<List<Property>>

    @Insert
    fun insert(property: Property): Long

    @Insert
    fun insertAll(properties: List<Property>)

    @Transaction
    @Query("SELECT * FROM property where idProperty = :idProperty")
    fun getOne(idProperty: Long): LiveData<PropertyAndPictures>
}