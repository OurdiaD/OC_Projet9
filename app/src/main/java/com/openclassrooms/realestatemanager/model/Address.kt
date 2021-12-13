package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo

data class Address(
       @ColumnInfo(name = "number") val number: String?,
       @ColumnInfo(name = "street")val street: String?,
       @ColumnInfo(name = "city") val city: String?,
       @ColumnInfo(name = "post_code") val postCode: String?
)