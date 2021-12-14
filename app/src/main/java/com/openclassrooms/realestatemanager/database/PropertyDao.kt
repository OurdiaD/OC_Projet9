package com.openclassrooms.realestatemanager.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.realestatemanager.model.Property

@Dao
interface PropertyDao {

    @Query("SELECT * FROM property")
    fun getAll(): LiveData<List<Property>>

    @Insert
    fun insert(property: Property)

    @Query("SELECT * FROM property where idProperty = :idProperty")
    fun getOne(idProperty: Long): LiveData<Property>
}