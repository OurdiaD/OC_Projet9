package com.openclassrooms.realestatemanager.ui.detailsProperty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsPropertyBinding
import com.openclassrooms.realestatemanager.utils.CarouselUtils
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class DetailsPropertyFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsPropertyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        binding = FragmentDetailsPropertyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        container?.removeAllViews()
        CarouselUtils().initCarousel(binding.carousel)
        getDetails()
        return root
    }

    private fun getDetails(){
        val bundle = arguments
        val id = bundle?.getLong("id_property")
        if (id != null) {
            detailsViewModel.getOneProperty(id).observe(viewLifecycleOwner, {
                binding.detailsSurface.text = it.property.surfaceArea.toString()
                binding.detailsPrice.text = it.property.price.toString()
                binding.detailsDescribe.text = it.property.describe.toString()
                binding.detailsRooms.text = it.property.numberOfRooms.toString()
                binding.carousel.registerLifecycle(lifecycle)
                val list = mutableListOf<CarouselItem>()
                for (pic in it.pictures) {
                    list.add(CarouselItem(imageUrl = pic.linkPic))
                }
                binding.carousel.setData(list)
            })
        }
    }
}