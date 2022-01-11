package com.openclassrooms.realestatemanager.model

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Property(
    @ColumnInfo(name = "type") var type: Int? = 0,
    @ColumnInfo(name = "price") var price: Int? = 0,
    @ColumnInfo(name = "surfaceArea") var surfaceArea: Int? = 0) {

    @PrimaryKey(autoGenerate = true) var idProperty: Long = 0
    @ColumnInfo(name = "numberOfRooms") var numberOfRooms = 0
    @ColumnInfo(name = "describe") var describe: String? = null
    @ColumnInfo(name = "location")  var location: String? = null
    @ColumnInfo(name = "status")  var status: Int? = null
    @ColumnInfo(name = "dateIn")  var dateIn: Long = Date().time
    @ColumnInfo(name = "dateSell") var dateSell: Long? = null
    @ColumnInfo(name = "agent") var agent: String? = null
    @ColumnInfo(name = "position") var position: String? = null
    @Embedded
    var address: Address? = null
    @Embedded
    var pointsOfInterest: PointsOfInterest? = null


    companion object {
        fun fromContentValues(values: ContentValues): Property {
            val item = Property()
            if (values.containsKey("idProperty")) item.idProperty = values.getAsLong("idProperty")
            if (values.containsKey("type")) item.type = values.getAsInteger("type")
            if (values.containsKey("price")) item.price = values.getAsInteger("price")
            if (values.containsKey("surfaceArea")) item.surfaceArea = values.getAsInteger("surfaceArea")
            if (values.containsKey("numberOfRooms")) item.numberOfRooms = values.getAsInteger("numberOfRooms")
            return item
        }
    }
}