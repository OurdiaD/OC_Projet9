package com.openclassrooms.realestatemanager.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ItemCarouselCustomBinding
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage

class CarouselUtils {

    fun initCarousel(carousel: ImageCarousel) {

        carousel.carouselListener =  object : CarouselListener {
            override fun onCreateViewHolder(
                layoutInflater: LayoutInflater,
                parent: ViewGroup
            ): ViewBinding? {
                return ItemCarouselCustomBinding.inflate(layoutInflater, parent, false)
            }

            override fun onBindViewHolder(
                binding: ViewBinding,
                item: CarouselItem,
                position: Int
            ) {
                val currentBinding = binding as ItemCarouselCustomBinding

                currentBinding.imageView.apply {
                    //scaleType = imageScaleType

                    // carousel_default_placeholder is the default placeholder comes with
                    // the library.
                    setImage(item, R.drawable.carousel_default_placeholder)
                }
            }
        }
    }
}