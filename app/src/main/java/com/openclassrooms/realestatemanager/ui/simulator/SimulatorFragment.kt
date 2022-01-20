package com.openclassrooms.realestatemanager.ui.simulator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.databinding.FragmentSimulatorBinding
import java.text.NumberFormat
import kotlin.math.pow

class SimulatorFragment : Fragment() {

    private lateinit var simulatorViewModel: SimulatorViewModel
    private var _binding: FragmentSimulatorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        simulatorViewModel =
            ViewModelProvider(this).get(SimulatorViewModel::class.java)

        _binding = FragmentSimulatorBinding.inflate(inflater, container, false)
        val root: View = binding.root

       /* val textView: TextView = binding.textSlideshow
        simulatorViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        binding.calculate.setOnClickListener {
            calculate()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calculate() {
        var amount = binding.amountEdit.text.toString().toFloat()
        val deposit = binding.depositEdit.text.toString().toFloat()
        val years = binding.yearsEdit.text.toString().toFloat()
        val rate = binding.rateEdit.text.toString().toFloat()

        amount -= deposit
        val month = years * 12
        val tMonth = (rate/12)/100
        val r = (1- ((1 + tMonth).toDouble()).pow((-month).toDouble())) /tMonth
        val valByMonth = amount / r

        val value = (valByMonth * 12) * years
        val ratePrice = value - amount

        binding.monthPrice.text = valByMonth.toInt().toString()
        binding.totalPrice.text = value.toInt().toString()
        binding.ratePrice.text = ratePrice.toInt().toString()
    }
}