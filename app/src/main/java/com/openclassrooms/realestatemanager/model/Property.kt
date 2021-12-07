package com.openclassrooms.realestatemanager.model

import android.graphics.Picture
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Property(
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "price") val price: Int?,
    @ColumnInfo(name = "surfaceArea") val surfaceArea: Float?) {

    @PrimaryKey(autoGenerate = true) val idProperty: Int = 0
    @ColumnInfo(name = "numberOfRooms") val numberOfRooms = 0
    @ColumnInfo(name = "describe") val describe: String? = null
    @ColumnInfo(name = "pictures")  val pictures: List<Picture>? = null
    @ColumnInfo(name = "location")  val location: String? = null
    @ColumnInfo(name = "pointsOfInterest")  val pointsOfInterest: String? = null
    @ColumnInfo(name = "status")  val status: String? = null
    @ColumnInfo(name = "dateIn")  val dateIn: Date = Date()
    @ColumnInfo(name = "dateSell") val dateSell: Date? = null
    @ColumnInfo(name = "agent") val agent: String? = null
    @ColumnInfo(name = "position") val position: String? = null
}