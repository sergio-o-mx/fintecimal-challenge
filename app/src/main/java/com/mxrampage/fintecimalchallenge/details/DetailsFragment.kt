package com.mxrampage.fintecimalchallenge.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mxrampage.fintecimalchallenge.R
import com.mxrampage.fintecimalchallenge.database.PlacesRoomModel
import com.mxrampage.fintecimalchallenge.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var placesMap: GoogleMap
    private lateinit var detailsBinding: FragmentDetailsBinding
    private var placeId: Long = 0
    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        detailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        detailsBinding.lifecycleOwner = viewLifecycleOwner
        detailsBinding.detailsViewModel = detailsViewModel
        detailsBinding.mapPlaces.onCreate(savedInstanceState)
        detailsBinding.mapPlaces.getMapAsync(this)
        placeId = DetailsFragmentArgs.fromBundle(requireArguments()).id
        setupUIManagerObserver()
        return detailsBinding.root
    }

    override fun onResume() {
        super.onResume()
        detailsBinding.mapPlaces.onResume()
        detailsViewModel.getSpecificPlaceById(placeId)
    }

    override fun onPause() {
        super.onPause()
        detailsBinding.mapPlaces.onPause()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        placesMap = googleMap
    }

    private fun setupUIManagerObserver() {
        detailsViewModel.place.observe(viewLifecycleOwner, {
            when (it) {
                DetailsUIStateManager.Loading -> {
                    detailsBinding.progressBar.visibility = View.VISIBLE
                }
                is DetailsUIStateManager.QueryResponse -> {
                    detailsBinding.progressBar.visibility = View.GONE
                    val place = it.place
                    val placeLocation = LatLng(place.entryLocation.latitude, place.entryLocation.longitude)
                    val icon = ContextCompat.getDrawable(
                        requireContext(),
                        if (place.isVisited) R.drawable.ic_visited_marker else R.drawable.ic_non_visited_marker
                    )?.let { drawable ->
                        MarkerUtils.getMarkerIcon(drawable)
                    }
                    val marker = MarkerOptions().position(placeLocation).icon(icon)
                    placesMap.animateCamera(CameraUpdateFactory.newLatLngZoom(placeLocation, 15f))
                    placesMap.addMarker(marker)
                    if (place.isVisited) loadVisitedDialogAndSetupInfo(place)
                    else loadPendingVisitDialogAndSetupInfo(place)
                }
                is DetailsUIStateManager.UpdateResponse -> {
                    detailsBinding.progressBar.visibility = View.GONE
                    if (it.updated == 1) requireActivity().onBackPressed()
                    else Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun loadPendingVisitDialogAndSetupInfo(place: PlacesRoomModel) {
        detailsBinding.pendingVisitDialog.root.visibility = View.VISIBLE
        detailsBinding.pendingVisitDialog.textStreetName.text = place.entryStreetName
        detailsBinding.pendingVisitDialog.textSuburbName.text = place.entrySuburb
        detailsBinding.pendingVisitDialog.textNavigateClickable.setOnClickListener {
            val url = "https://www.google.com/maps/dir/?api=1&destination=${place.entryLocation.latitude},${place.entryLocation.longitude}&travelmode=driving"
            val googleMapsIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(googleMapsIntent)
        }
        detailsBinding.pendingVisitDialog.textVisitClickable.setOnClickListener {
            detailsViewModel.markPlaceAsVisited(place)
        }
    }

    private fun loadVisitedDialogAndSetupInfo(place: PlacesRoomModel) {
        detailsBinding.visitedDialog.root.visibility = View.VISIBLE
        detailsBinding.visitedDialog.textStreetName.text = place.entryStreetName
        detailsBinding.visitedDialog.textSuburbName.text = place.entrySuburb
    }
}
