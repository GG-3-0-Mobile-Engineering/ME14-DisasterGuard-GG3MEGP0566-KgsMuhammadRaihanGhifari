package com.hann.disasterguard.presentation.map

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.hann.disasterguard.R
import com.hann.disasterguard.databinding.FragmentMapBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding :FragmentMapBinding
    private lateinit var mMap : GoogleMap
    private val viewModel: MapViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        setMapStyle()

        viewModel.getReportLive(null)

        viewModel.state.observe(viewLifecycleOwner){
            if (it.isLoading){
                Log.d("map loading", "map loading")
            }
            if (it.error.isNotBlank()){
                Log.d("map error", "map error")
            }
            if (it.map.isNotEmpty()){
                for (i in it.map){
                    Log.d("map ada", "${i.coordinates[1]}, ${i.coordinates[0]}")
                    mMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(i.coordinates[1], i.coordinates[0]))
                            .title(i.properties.disaster_type)
                            .snippet(i.properties.text)
                            .alpha(0.8f)
                    )
                }
            }
            if (it.map.isEmpty()){
                Log.d("map empty", it.map.toString())
            }
        }
    }

    private fun setMapStyle() {
        try {
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
        } catch (_: Resources.NotFoundException) {
        }
    }

}