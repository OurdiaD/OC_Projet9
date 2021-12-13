package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Property(
    @ColumnInfo(name = "type") var type: Int?,
    @ColumnInfo(name = "price") var price: Int?,
    @ColumnInfo(name = "surfaceArea") var surfaceArea: Int?) {

    @PrimaryKey(autoGenerate = true) var idProperty: Long = 0
    @ColumnInfo(name = "numberOfRooms") var numberOfRooms = 0
    @ColumnInfo(name = "describe") var describe: String? = null
    @ColumnInfo(name = "location")  var location: String? = null
    @ColumnInfo(name = "pointsOfInterest")  var pointsOfInterest: String? = null
    @ColumnInfo(name = "status")  var status: String? = null
    @ColumnInfo(name = "dateIn")  var dateIn: Long = Date().time
    @ColumnInfo(name = "dateSell") var dateSell: Long? = null
    @ColumnInfo(name = "agent") var agent: String? = null
    @ColumnInfo(name = "position") var position: String? = null
    @Embedded
    var address: Address? = null
}