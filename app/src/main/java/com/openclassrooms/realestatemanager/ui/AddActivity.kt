package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Property
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class AddActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityAddBinding
    private val REQUEST_CODE = 100
    private lateinit var carousel: ImageCarousel
    val listPic = mutableListOf<CarouselItem>()
    val listPicString = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addProperty.setOnClickListener {
            saveData()
            Log.d("lol add", "click")
        }

        binding.addPicGallery.setOnClickListener {
            openGalleryForImage()
        }

        carousel()
    }

    private fun initSpinners() {
        val typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, R.layout.item_spiner)
        binding.typePropertySpinner.setAdapter(typeAdapter)
        binding.typePropertySpinner.setSelection(0)

        val statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, R.layout.item_spiner)
        binding.statusPropertySpinner.setAdapter(statusAdapter)
        binding.statusPropertySpinner.setSelection(0)
    }

    private fun saveData() {
        val type =  binding.typePropertySpinner.selectedItemPosition
        val status = binding.statusPropertySpinner.selectedItemPosition
        val price  = Integer.parseInt(binding.pricePropertyEdit.text.toString())
        val surface = Integer.parseInt(binding.surfacePropertyEdit.text.toString())
        val rooms = Integer.parseInt(binding.roomsPropertyEdit.text.toString())
        val describe = binding.describePropertyEdit.text.toString()
        val number = binding.addressNumberEdit.text.toString()
        val street = binding.addressStreetEdit.text.toString()
        val postalCode = binding.addressPostCodeEdit.text.toString()
        val city = binding.addressCityEdit.text.toString()

        val address = Address(number, street, city, postalCode)

        val newProperty = Property(type, price, surface)
        newProperty.status = status
        newProperty.numberOfRooms = rooms
        newProperty.describe = describe
        newProperty.address = address

        val repo = PropertyRepository(application)
        repo.addProperty(newProperty, listPicString)
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            //binding.gridPic.setImageURI(data?.data) // handle chosen image
            listPic.add(CarouselItem(imageUrl = data?.data.toString()))
            listPicString.add(data?.data.toString())

            Log.d("lol add", "toString " + data?.data.toString())
            Log.d("lol add", "path " + data?.data?.path)
            Log.d("lol add", "encodedpath " + data?.data?.encodedPath)
            Log.d("lol add", "encodedquery " + data?.data?.encodedQuery)
            carousel.setData(listPic)
            /*data?.data?.let {
                *//*val inputStream = contentResolver.openInputStream(it)
                val drawable = Drawable.createFromStream(inputStream, it.toString())
                CarouselItem(imageDrawable = R.drawable.ic_add_photo)*//*
                listPic.add(CarouselItem(imageUrl = it.path))
            }*/
        }
    }

    fun carousel() {
        carousel = binding.carousel
        carousel.registerLifecycle(lifecycle)
    }
}