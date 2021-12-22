package com.openclassrooms.realestatemanager.database

import androidx.room.*
import com.openclassrooms.realestatemanager.model.Picture

@Dao
interface PictureDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPic(picture: Picture)

    @Query("DELETE FROM picture WHERE propertyId = :id AND linkPic = :path")
    fun deletePic(id: Long, path: String)
}