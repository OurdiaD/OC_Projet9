package com.openclassrooms.realestatemanager.ui.detailsProperty

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityDetailsBinding

class DetailsPropertyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getFragment()
    }

    private fun getFragment() {
        val id = intent.extras?.getLong("id_property")
        val bundle = Bundle()
        id?.let { bundle.putLong("id_property", it) }
        val fragment = DetailsPropertyFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.frame_layout_detail, fragment).commit()
    }
}