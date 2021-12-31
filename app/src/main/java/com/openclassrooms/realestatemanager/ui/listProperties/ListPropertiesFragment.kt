package com.openclassrooms.realestatemanager.ui.listProperties

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.databinding.FragmentListPropertiesBinding

class ListPropertiesFragment : Fragment() {

    private lateinit var listPropertiesModel: ListPropertiesModel
    private var _binding: FragmentListPropertiesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        listPropertiesModel = ViewModelProvider(this)[ListPropertiesModel::class.java]
        _binding = FragmentListPropertiesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        getList()

        return root
    }

    private fun getList() {
        val adapter = ListPropertiesAdapter()
        val list = listPropertiesModel.getAllProperties()
        list?.observe(viewLifecycleOwner, {
            Log.d("list lol", " "+"it.size")
            Log.d("list lol", " "+it.size)
            adapter.properties = it
            binding.listProperty.adapter = adapter
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}