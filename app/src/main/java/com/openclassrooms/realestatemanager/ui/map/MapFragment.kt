package com.openclassrooms.realestatemanager.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.databinding.FragmentMapBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.Utils.isInternetAvailable


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mapViewModel: MapViewModel
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mapFragment = childFragmentManager.findFragmentByTag("mapFragment") as SupportMapFragment

        return root
    }

    override fun onResume() {
        super.onResume()
        isInternetAvailable(context).observe(viewLifecycleOwner, { success ->
            Log.d("lol co", "" + success)
            if (success) {
                binding.internetFail.visibility = View.GONE
                mapFragment.getMapAsync(this)
            } else {
                binding.internetFail.visibility = View.VISIBLE
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mapViewModel.getAllProperties()?.observe(viewLifecycleOwner, {
            map.clear()
            for (property in it){
                val location = Utils.getLocalisation(context, property.property.address)
                if (location != null) {
                    val loc = location.split(",")
                    val latlng = LatLng(loc[0].toDouble(), loc[1].toDouble())
                    map.addMarker(MarkerOptions().position(latlng).title(Utils.getAddressToString(property.property.address)))
                }
            }

            if (hasPermissions()) getCurrentLocation()
        })
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mMap.isMyLocationEnabled = true
        val locationResult = fusedLocationClient.lastLocation
        locationResult.addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful) {
                val current = LatLng(it.result.latitude, it.result.longitude)
                val myPosition = CameraPosition.Builder()
                    .target(current).zoom(8f).bearing(90f).tilt(30f).build()
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(myPosition))
            }
        }
    }

    private fun hasPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PackageManager.PERMISSION_GRANTED
            )
            return false
        } else {
            return true
        }
    }
}