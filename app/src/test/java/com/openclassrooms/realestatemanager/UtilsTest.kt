package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class UtilsTest {

    @Test
    fun convertDollarToEuroTest() {
        val value: Int = Utils.convertDollarToEuro(100)
        assertEquals(81, value)
    }

    @Test
    fun convertEuroToDollarTest() {
        val value: Int = Utils.convertEuroToDollar(81)
        assertEquals(100, value)
    }

    @Test
    fun getTodayDateTest() {
        val value: String = Utils.getTodayDate()
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.format(Date())
        assertEquals(date, value)
    }
}