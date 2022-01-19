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

    fun calculate() {
        val amount = binding.amountEdit.text.toString().toFloat()
        val years = binding.yearsEdit.text.toString().toFloat()
        val rate = binding.rateEdit.text.toString().toFloat()
        val rateAssure = binding.rateAssurEdit.text.toString().toFloat()

        val month = years * 12
        val valRate = amount * (rate / 10)
        val valAssure = amount * (rateAssure / 10)
        val value = amount + valRate + valAssure

        val valByMonth = value / month

        binding.monthPrice.text = valByMonth.toString()
        binding.totalPrice.text = value.toString()
        binding.ratePrice.text = valRate.toString()
        binding.ratePriceAssure.text = valAssure.toString()

    }
}