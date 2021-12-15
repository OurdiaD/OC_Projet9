package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsPropertyBinding
import com.openclassrooms.realestatemanager.databinding.FragmentListPropertiesBinding
import com.openclassrooms.realestatemanager.ui.listProperties.ListPropertiesModel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class DetailsPropertyFragment : Fragment() {

    private var binding: FragmentDetailsPropertyBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //listPropertiesModel = ViewModelProvider(this).get(ListPropertiesModel::class.java)
        binding = FragmentDetailsPropertyBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        container?.removeAllViews()
        getDetails()
        return root
    }

    fun getDetails(){
        val bundle = arguments
        val id = bundle?.getLong("id_property")
        val repo = context?.let { PropertyRepository(it) }
        if (id != null) {
            repo?.getOne(id)!!.observe(viewLifecycleOwner, {
                binding?.detailsSurface?.text = it.property.surfaceArea.toString()
                binding?.detailsPrice?.text = it.property.price.toString()
                binding?.detailsDescribe?.text = it.property.describe.toString()
                binding?.detailsRooms?.text = it.property.numberOfRooms.toString()
                binding?.carousel?.registerLifecycle(lifecycle)
                val list = mutableListOf<CarouselItem>()
                for (pic in it.pictures) {
                    list.add(CarouselItem(imageUrl = pic.linkPic))
                }
                binding?.carousel?.setData(list)
            })
        }
    }
}