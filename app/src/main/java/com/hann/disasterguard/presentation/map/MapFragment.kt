package com.hann.disasterguard.presentation.map

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.hann.disasterguard.R
import com.hann.disasterguard.coreapp.domain.model.DisasterType
import com.hann.disasterguard.coreapp.ui.DisasterTypeAdapter
import com.hann.disasterguard.databinding.FragmentMapBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding :FragmentMapBinding
    private lateinit var mMap : GoogleMap
    private lateinit var adapter: DisasterTypeAdapter
    private val viewModel: MapViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        getListType()
    }

    private fun getListType() {
        val dataId = resources.getIntArray(com.hann.disasterguard.coreapp.R.array.data_type_id)
        val dataName = resources.getStringArray(com.hann.disasterguard.coreapp.R.array.data_type_disaster)
        val listType = ArrayList<DisasterType>()
        for (i in dataId.indices) {
            val type = DisasterType(dataId[i], dataName[i])
            listType.add(type)
        }
        adapter.setData(listType)
    }

    private fun initRecyclerView() {
        adapter = DisasterTypeAdapter()
        binding.rvDisasterType.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDisasterType.adapter = adapter
        binding.rvDisasterType.setHasFixedSize(false)
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