package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Picture {
    @PrimaryKey(autoGenerate = true) var idPicture: Int = 0
    @ColumnInfo(name = "idProperty") var idProperty = 0
}