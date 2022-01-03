package com.openclassrooms.realestatemanager.ui.detailsProperty

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsPropertyBinding
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.PointsOfInterest
import com.openclassrooms.realestatemanager.ui.AddActivity
import com.openclassrooms.realestatemanager.utils.CarouselUtils
import com.openclassrooms.realestatemanager.utils.Utils
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class DetailsPropertyFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsPropertyBinding
    private var idProperty: Long? = null
    var fullscreen: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        binding = FragmentDetailsPropertyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        CarouselUtils().initCarousel(binding.carousel, activity)
        //listnerCarousel()
        getDetails()
        setHasOptionsMenu(true)
        return root
    }

    private fun getDetails(){
        val bundle = arguments
        idProperty = bundle?.getLong("id_property")
        if (idProperty != null) {
            detailsViewModel.getOneProperty(idProperty!!).observe(viewLifecycleOwner, {
                binding.detailsSurface.text = it.property.surfaceArea.toString()
                binding.detailsPrice.text = it.property.price.toString()
                binding.detailsDescribe.text = it.property.describe.toString()
                binding.detailsRooms.text = it.property.numberOfRooms.toString()

                toogleCarrousel(it.pictures)
                binding.carousel.registerLifecycle(lifecycle)
                val list = mutableListOf<CarouselItem>()
                for (pic in it.pictures) {
                    list.add(CarouselItem(imageUrl = pic.linkPic))
                }
                binding.carousel.setData(list)


                Utils.isInternetAvailable(context).observe(viewLifecycleOwner, { success ->
                    Log.d("lol co", "" + success)
                    if (success) {
                        binding.detailsMap.visibility = View.VISIBLE
                        binding.internetFail.visibility = View.GONE
                        val location = Utils.getLocalisation(context,it.property.address)
                        if (location != null) {
                            val url = "https://maps.googleapis.com/maps/api/staticmap?center="+location+
                                    "&zoom=15&size=300x300&maptype=roadmap&markers=color:red%7Clabel:C%7C" +location+
                                    "&key=" + BuildConfig.API_KEY
                            Glide.with(requireContext()).load(url).into(binding.detailsMap)
                        }
                    } else {
                        binding.detailsMap.visibility = View.GONE
                        binding.internetFail.visibility = View.VISIBLE
                    }
                })


                getPointsInterest(it.property.pointsOfInterest)
            })
        }
    }

    private fun getPointsInterest(points: PointsOfInterest?) {
        if (points != null) {
            binding.detailsHealth.visibility = checkPoints(points.health)
            binding.detailsSchool.visibility = checkPoints(points.school)
            binding.detailsMarket.visibility = checkPoints(points.market)
            binding.detailsTransport.visibility = checkPoints(points.transports)
            binding.detailsRestaurant.visibility = checkPoints(points.restaurant)
            binding.detailsPark.visibility = checkPoints(points.park)
        }
    }

    private fun checkPoints(point: Boolean): Int {
        return if (point)
            View.VISIBLE
        else
            View.GONE
    }

    fun toogleCarrousel(list: List<Picture>) {
        if(list.size > 0){
            binding.carousel.visibility = View.VISIBLE
        } else {
            binding.carousel.visibility = View.GONE
        }
    }

    fun listnerCarousel() {
        binding.carousel.carouselListener = object  : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                super.onClick(position, carouselItem)
                Log.d("lol add", "click")
                if(fullscreen) {
                    binding.carousel.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 400)
                    fullscreen = false
                } else {
                    val mDisplay: Display = activity!!.windowManager.defaultDisplay
                    val width: Int = mDisplay.width
                    val height: Int = mDisplay.height
                    binding.carousel.layoutParams = ConstraintLayout.LayoutParams(width, height)
                    fullscreen = true
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit) {
            val intent = Intent(context, AddActivity::class.java)
            intent.putExtra("id_property", idProperty)
            ContextCompat.startActivity(requireContext(), intent, null)
        }
        return super.onOptionsItemSelected(item)
    }
}