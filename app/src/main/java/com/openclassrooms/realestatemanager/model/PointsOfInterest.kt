package com.openclassrooms.realestatemanager.model

data class PointsOfInterest(
    var health:Boolean = false,
    var school: Boolean = false,
    var market: Boolean = false,
    var transports:Boolean = false,
    var restaurant:Boolean = false,
    var park:Boolean = false
)