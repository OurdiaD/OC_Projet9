package com.openclassrooms.realestatemanager.ui.detailsProperty

import android.os.Bundle
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityDetailsBinding
import com.openclassrooms.realestatemanager.ui.CommonActivity

class DetailsPropertyActivity : CommonActivity() {
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