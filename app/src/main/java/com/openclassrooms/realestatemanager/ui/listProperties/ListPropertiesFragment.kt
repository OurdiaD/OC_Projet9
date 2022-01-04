package com.openclassrooms.realestatemanager.ui.listProperties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentListPropertiesBinding
import com.openclassrooms.realestatemanager.ui.detailsProperty.DetailsPropertyFragment

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
            adapter.properties = it
            binding.listProperty.adapter = adapter
            val isTablet = resources.getBoolean(R.bool.isTablet)
            if (isTablet) {
                val idProperty = it[0].property.idProperty
                configureAndShowDetailFragment(idProperty)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configureAndShowDetailFragment(idProperty: Long) {
        var detailFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.frame_layout_detail) as DetailsPropertyFragment?

        //A - We only add DetailFragment in Tablet mode (If found frame_layout_detail)
        if (detailFragment == null && activity?.findViewById<View?>(R.id.frame_layout_detail) != null) {
            val bundle = Bundle()
            bundle.putLong("id_property", idProperty)
            detailFragment = DetailsPropertyFragment()
            detailFragment.arguments = bundle
            activity?.supportFragmentManager!!.beginTransaction()
                .add(R.id.frame_layout_detail, detailFragment)
                .commit()
        }
    }
}