package com.openclassrooms.realestatemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.openclassrooms.realestatemanager.model.Picture

@Dao
interface PictureDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPic(picture: Picture)
}