package com.openclassrooms.realestatemanager.database

import androidx.room.Dao
import androidx.room.Query
import com.openclassrooms.realestatemanager.model.Property

@Dao
interface PropertyDao {

    @Query("SELECT * FROM property")
    fun getAll(): List<Property>
}