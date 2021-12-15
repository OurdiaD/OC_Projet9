package com.openclassrooms.realestatemanager.database

import androidx.room.Dao
import androidx.room.Insert
import com.openclassrooms.realestatemanager.model.Picture

@Dao
interface PictureDao {

    @Insert
    fun insertPic(picture: Picture)
}