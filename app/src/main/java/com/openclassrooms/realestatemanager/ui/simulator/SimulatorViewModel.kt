package com.openclassrooms.realestatemanager.ui.simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.pow

class SimulatorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    var valByMonthString: String = ""
    var valueString: String = ""
    var ratePriceString: String = ""

    fun calculate(amount: Float, deposit: Float, years: Float, rate: Float) {
        val total = amount - deposit
        val month = years * 12
        val tMonth = (rate/12)/100
        val r = (1- ((1 + tMonth).toDouble()).pow((-month).toDouble())) /tMonth
        val valByMonth = total / r
        val value = (valByMonth * 12) * years
        val ratePrice = value - total

        valByMonthString = valByMonth.toInt().toString()
        valueString = value.toInt().toString()
        ratePriceString = ratePrice.toInt().toString()
    }
}