package com.openclassrooms.realestatemanager.model

import androidx.room.Embedded
import androidx.room.Relation

data class PropertyAndPictures(
    @Embedded val property: Property,
    @Relation(
        parentColumn = "idProperty",
        entityColumn = "propertyId"
    )
    val pictures: List<Picture>
)
