package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Picture(
    @ColumnInfo(name = "propertyId") var propertyId: Long,
    @ColumnInfo(name = "linkPic") var linkPic: String
) {
    @PrimaryKey(autoGenerate = true) var idPicture: Int = 0

}