package com.openclassrooms.realestatemanager.ui.map

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapViewModel: MapViewModel
    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapFragment = childFragmentManager.findFragmentByTag("mapFragment") as SupportMapFragment?
        mapFragment?.getMapAsync(this);
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: GoogleMap) {
        val geocoder = Geocoder(context)
        mapViewModel.getAllProperties().observe(viewLifecycleOwner, {
            for (property in it){
                val address = property.address?.number + " " +
                        property.address?.street + " " +
                        property.address?.postCode + " " +
                        property.address?.city

                val pos = geocoder.getFromLocationName(address, 1)

                if(pos.size > 0) {
                    val lat = pos.get(0).latitude
                    val lng = pos.get(0).longitude
                    val mark = LatLng(lat, lng)
                    map.addMarker(MarkerOptions().position(mark).title(address))
                }
            }
        })

        //map.moveCamera(CameraUpdateFactory.newLatLng(mark))
    }
}