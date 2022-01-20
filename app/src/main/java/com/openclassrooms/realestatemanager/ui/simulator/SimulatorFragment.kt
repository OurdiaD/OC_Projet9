package com.openclassrooms.realestatemanager.ui.simulator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.databinding.FragmentSimulatorBinding

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
    ): View {
        simulatorViewModel =
            ViewModelProvider(this)[SimulatorViewModel::class.java]

        _binding = FragmentSimulatorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.monthPrice.text = simulatorViewModel.valByMonthString
        binding.totalPrice.text = simulatorViewModel.valueString
        binding.ratePrice.text = simulatorViewModel.ratePriceString
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
        val amount = binding.amountEdit.text.toString().toFloat()
        val deposit = binding.depositEdit.text.toString().toFloat()
        val years = binding.yearsEdit.text.toString().toFloat()
        val rate = binding.rateEdit.text.toString().toFloat()

        simulatorViewModel.calculate(amount,deposit,years, rate)

        binding.monthPrice.text = simulatorViewModel.valByMonthString
        binding.totalPrice.text = simulatorViewModel.valueString
        binding.ratePrice.text = simulatorViewModel.ratePriceString
    }
}