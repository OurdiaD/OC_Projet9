package com.openclassrooms.realestatemanager.ui.listProperties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.database.PropertyRepository
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
        listPropertiesModel = ViewModelProvider(this).get(ListPropertiesModel::class.java)
        _binding = FragmentListPropertiesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        getList()

        return root
    }

    fun getList() {

        val adapter = ListPropertiesAdapter()
        adapter.supportFragmentManager = activity?.supportFragmentManager
        val repo = context?.let { PropertyRepository(it) }
        repo?.getAll()?.observe(viewLifecycleOwner, {
            adapter.properties = it
            binding.listProperty.adapter = adapter
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}