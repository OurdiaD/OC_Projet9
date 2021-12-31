package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Display
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.PointsOfInterest
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.editProperty.EditViewModel
import com.openclassrooms.realestatemanager.utils.CarouselUtils
import com.openclassrooms.realestatemanager.utils.Utils
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.model.CarouselType
import java.io.File


class AddActivity : CommonActivity()  {

    private lateinit var editViewModel: EditViewModel
    private var idProperty: Long? = null
    private var currentProperty: Property? = null
    private lateinit var binding: ActivityAddBinding
    private val REQUEST_CODE = 100

    private val listPic = mutableListOf<CarouselItem>()
    private val listPicString = mutableListOf<String>()
    private var datePic: String? = null
    var fullscreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editViewModel = ViewModelProvider(this)[EditViewModel::class.java]
        initView()
        idProperty = intent.extras?.getLong("id_property")
        if(idProperty != null)
            initData()
    }

    private fun initView() {
        binding.addProperty.setOnClickListener {
            saveData()
            finish()
        }

        binding.addPicGallery.setOnClickListener {
            openGalleryForImage()
        }

        binding.addPicture.setOnClickListener {
            takePicture()
        }

        binding.carousel.registerLifecycle(lifecycle)
        CarouselUtils().initCarousel(binding.carousel, this)
        listnerCarousel()
    }

    private fun initData() {
        binding.addProperty.setText(R.string.action_edit)
        val item = idProperty?.let { editViewModel.getOneProperty(it) }
        item?.observe(this) {
            currentProperty = it.property
            binding.typePropertySpinner.setSelection(it.property.type!!)
            binding.statusPropertySpinner.setSelection(it.property.status!!)
            binding.pricePropertyEdit.setText(it.property.price.toString())
            binding.surfacePropertyEdit.setText(it.property.surfaceArea.toString())
            binding.roomsPropertyEdit.setText(it.property.numberOfRooms.toString())
            binding.describePropertyEdit.setText(it.property.describe.toString())
            binding.addressNumberEdit.setText(it.property.address?.number.toString())
            binding.addressStreetEdit.setText(it.property.address?.street.toString())
            binding.addressPostCodeEdit.setText(it.property.address?.postCode.toString())
            binding.addressCityEdit.setText(it.property.address?.city.toString())

            for (pic in it.pictures) {
                listPic.add(CarouselItem(imageUrl = pic.linkPic))
                listPicString.add(pic.linkPic)
            }
            binding.carousel.setData(listPic)
            toogleCarrousel()
            showPointsInterest(it.property.pointsOfInterest)
        }

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

        var property = currentProperty
        if (property == null) {
             property = Property()
        }

        property.type = type
        property.price = price
        property.surfaceArea = surface
        property.status = status
        property.numberOfRooms = rooms
        property.describe = describe
        property.address = address
        property.pointsOfInterest = getPointOfInterest()

        if (idProperty == null) {
            editViewModel.addProperty(property, listPicString)
        } else {
            editViewModel.updateProperty(property, listPicString)
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun takePicture() {
        datePic = Utils.getTodayDatewHour()
        val f = File("${getExternalFilesDir(null)}/imgShot"+datePic)
        val photoURI = FileProvider.getUriForFile(this, "${packageName}.fileprovider", f)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .apply { putExtra(MediaStore.EXTRA_OUTPUT, photoURI) }
        startActivityForResult(intent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var path: String? = null
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            path = data?.data?.let { getImageFilePath(it) }.toString()
        } else if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            path = File("${getExternalFilesDir(null)}/imgShot"+datePic).toString()
        }
        if(path != null){
            listPic.add(CarouselItem(imageUrl = path))
            listPicString.add(path)
            binding.carousel.setData(listPic)
        }
        toogleCarrousel()
    }

    private fun getImageFilePath(uri: Uri): String {
        var path = ""
        if (contentResolver != null) {
            val cursor = contentResolver.query(uri, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }

    fun listnerCarousel() {
        binding.carousel.carouselListener = object  : CarouselListener {
            /*override fun onClick(position: Int, carouselItem: CarouselItem) {
                super.onClick(position, carouselItem)
                Log.d("lol add", "click")
                if(fullscreen) {
                    binding.carousel.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 400)
                    binding.carousel.carouselType = CarouselType.SHOWCASE
                    fullscreen = false
                } else {
                    val mDisplay: Display = windowManager.defaultDisplay
                    val width: Int = mDisplay.width
                    val height: Int = mDisplay.height
                    binding.carousel.layoutParams = ConstraintLayout.LayoutParams(width, height)
                    fullscreen = true
                }

            }*/

            override fun onLongClick(position: Int, carouselItem: CarouselItem) {
                super.onLongClick(position, carouselItem)
                Log.d("lol add", "longclick")
                listPic.remove(carouselItem)
                listPicString.remove(carouselItem.imageUrl)
                if (idProperty != null){
                    editViewModel.deletePicture(idProperty!!, carouselItem.imageUrl.toString())
                }
                binding.carousel.setData(listPic)
            }
        }
    }

    fun toogleCarrousel() {
        if(listPicString.size > 0){
            binding.carousel.visibility = VISIBLE
        } else {
            binding.carousel.visibility = GONE
        }
    }

    fun getPointOfInterest(): PointsOfInterest {
        val health = binding.addHealth.isChecked
        val school = binding.addSchool.isChecked
        val market = binding.addMarket.isChecked
        val transport = binding.addTransports.isChecked
        val restaurant = binding.addRestaurant.isChecked
        val park = binding.addPark.isChecked

        return PointsOfInterest(school, market, park, restaurant, health, transport)
    }


    private fun showPointsInterest(points: PointsOfInterest?) {
        if (points != null) {
            binding.addHealth.isChecked = points.health
            binding.addSchool.isChecked = points.school
            binding.addMarket.isChecked = points.market
            binding.addTransports.isChecked = points.transports
            binding.addRestaurant.isChecked = points.restaurant
            binding.addPark.isChecked = points.park
        }
    }

}