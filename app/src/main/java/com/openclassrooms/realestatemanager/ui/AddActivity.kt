package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.content.FileProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.PropertyRepository
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.utils.CarouselUtils
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.io.File


class AddActivity : CommonActivity()  {

    private lateinit var binding: ActivityAddBinding
    private val REQUEST_CODE = 100

    val listPic = mutableListOf<CarouselItem>()
    val listPicString = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initSpinners() {
        val typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, R.layout.item_spiner)
        binding.typePropertySpinner.setAdapter(typeAdapter)
        binding.typePropertySpinner.setSelection(0)

        val statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, R.layout.item_spiner)
        binding.statusPropertySpinner.setAdapter(statusAdapter)
        binding.statusPropertySpinner.setSelection(0)
    }

    fun initView() {
        binding.addProperty.setOnClickListener {
            saveData()
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

    private fun takePicture() {
        val f = File("${getExternalFilesDir(null)}/imgShot")
        val photoURI = FileProvider.getUriForFile(this, "${packageName}.fileprovider", f)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .apply { putExtra(MediaStore.EXTRA_OUTPUT, photoURI) }
        startActivityForResult(intent, 1234)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lateinit var path: String
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            path = data?.data?.let { getImageFilePath(it) }.toString()
        } else if (requestCode == 1234 && resultCode == Activity.RESULT_OK) {
            path = File("${getExternalFilesDir(null)}/imgShot").toString()
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