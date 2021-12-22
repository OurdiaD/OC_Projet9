package com.openclassrooms.realestatemanager.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ItemCarouselCustomBinding
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.model.CarouselType
import org.imaginativeworld.whynotimagecarousel.utils.setImage

class CarouselUtils {

    fun initCarousel(carousel: ImageCarousel, activity:Activity? = null) {
        var fullscreen = false
        carousel.carouselListener =  object : CarouselListener {
            override fun onCreateViewHolder(
                layoutInflater: LayoutInflater,
                parent: ViewGroup
            ): ViewBinding {
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


                /*currentBinding.imageView.setOnClickListener(){
                    Log.d("lol caroutils", "click")
                    if(fullscreen) {
                        carousel.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 400)
                        carousel.carouselType = CarouselType.BLOCK
                        fullscreen = false
                    } else {
                        val mDisplay: Display = activity!!.windowManager.defaultDisplay
                        val width: Int = mDisplay.width
                        val height: Int = mDisplay.height
                        carousel.layoutParams = ConstraintLayout.LayoutParams(width, height)
                        carousel.carouselType = CarouselType.BLOCK
                        fullscreen = true
                    }
                }*/
            }

            /*override fun onClick(position: Int, carouselItem: CarouselItem) {
                super.onClick(position, carouselItem)
                Log.d("lol add", "click")
                *//*if(fullscreen) {
                    binding.carousel.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 400)
                    binding.carousel.carouselType = CarouselType.SHOWCASE
                    fullscreen = false
                } else {
                    val mDisplay: Display = windowManager.defaultDisplay
                    val width: Int = mDisplay.width
                    val height: Int = mDisplay.height
                    binding.carousel.layoutParams = ConstraintLayout.LayoutParams(width, height)
                    fullscreen = true
                }*//*

            }*/

           /* override fun onLongClick(position: Int, carouselItem: CarouselItem) {
                super.onLongClick(position, carouselItem)
                Log.d("lol add", "longclick")
                *//*listPic.remove(carouselItem)
                listPicString.remove(carouselItem.imageUrl)
                if (idProperty != null){
                    editViewModel.deletePicture(idProperty!!, carouselItem.imageUrl.toString())
                }
                binding.carousel.setData(listPic)*//*
            }*/


          /*  override fun onClick(position: Int, carouselItem: CarouselItem) {
                super.onClick(position, carouselItem)
                Log.d("lol add", "click")
                context.win
                val mDisplay: Display = context?.getSystemService(WindowManager::class.java)!!.defaultDisplay
                val width: Int = mDisplay.width
                val height: Int = mDisplay.height
                carousel.layoutParams = ConstraintLayout.LayoutParams(width, height)
            }*/
        }
    }
}