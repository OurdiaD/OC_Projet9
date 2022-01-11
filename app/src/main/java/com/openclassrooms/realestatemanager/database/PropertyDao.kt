package com.openclassrooms.realestatemanager.database

import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyAndPictures
import androidx.room.Delete




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

    @Query("SELECT * FROM property where idProperty = :idProperty")
    fun getItemsWithCursor(idProperty: Long): Cursor

    @Query("SELECT * FROM property")
    fun getAllsItemsWithCursor(): List<Cursor>

    @Query("DELETE FROM property where idProperty = :idProperty")
    fun delete(idProperty: Long)

    @Query("DELETE FROM property")
    fun deleteAll()
}