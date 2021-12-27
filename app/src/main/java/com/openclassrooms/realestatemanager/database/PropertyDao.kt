package com.openclassrooms.realestatemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyAndPictures

@Dao
interface PropertyDao {

    @Transaction
    @Query("SELECT * FROM property")
    fun getAllWithPictures(): List<PropertyAndPictures>

    @Query("SELECT * FROM property")
    fun getAll(): LiveData<List<Property>>

    @RawQuery
    fun getQuery(query: SupportSQLiteQuery): List<PropertyAndPictures>

    @Insert
    fun insert(property: Property): Long

    @Insert
    fun insertAll(properties: List<Property>)

    @Transaction
    @Query("SELECT * FROM property where idProperty = :idProperty")
    fun getOne(idProperty: Long): LiveData<PropertyAndPictures>

    @Update
    fun update(vararg property: Property)
}