package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.utils.CarouselUtils
import com.openclassrooms.realestatemanager.utils.Utils
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.io.File


class AddActivity : CommonActivity()  {

    private var idProperty: Long? = null
    private var currentProperty: Property? = null
    private lateinit var binding: ActivityAddBinding
    private val REQUEST_CODE = 100

    private val listPic = mutableListOf<CarouselItem>()
    private val listPicString = mutableListOf<String>()
    private var datePic: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        idProperty = intent.extras?.getLong("id_property")
        if(idProperty != null)
            initData()
    }

    private fun initView() {
        binding.addProperty.setOnClickListener {
            saveData()
            finish()
            Log.d("lol add", "click")
        }

        binding.addPicGallery.setOnClickListener {
            openGalleryForImage()
        }

        binding.addPicture.setOnClickListener {
            takePicture()
        }

        binding.carousel.registerLifecycle(lifecycle)
        CarouselUtils().initCarousel(binding.carousel)
    }

    private fun initData() {
        binding.addProperty.setText(R.string.action_edit)
        val repo = PropertyRepository(application)
        val item = idProperty?.let { repo.getOne(it) }
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


            binding.carousel.registerLifecycle(lifecycle)
            for (pic in it.pictures) {
                listPic.add(CarouselItem(imageUrl = pic.linkPic))
                listPicString.add(pic.linkPic)
            }
            binding.carousel.setData(listPic)
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

        val repo = PropertyRepository(application)
        if (idProperty == null) {
            repo.addProperty(property, listPicString)
        } else {
            repo.updateProperty(property, listPicString)
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
        lateinit var path: String
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            path = data?.data?.let { getImageFilePath(it) }.toString()
        } else if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            path = File("${getExternalFilesDir(null)}/imgShot"+datePic).toString()
        }
        listPic.add(CarouselItem(imageUrl = path))
        listPicString.add(path)
        binding.carousel.setData(listPic)
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
}