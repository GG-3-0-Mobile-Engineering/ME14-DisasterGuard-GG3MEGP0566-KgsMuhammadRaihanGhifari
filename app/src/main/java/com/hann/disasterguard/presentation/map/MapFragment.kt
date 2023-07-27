package com.hann.disasterguard.presentation.map

import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
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
    private  var typeDisaster : String? = null
    private  var regionDisaster : String? = null

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
        initListRegion()
        getListType()
    }

    private fun initListRegion() {
        val region = resources.getStringArray(R.array.province)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, region)
        binding.autoTextViewRegion.setAdapter(adapter)

        binding.autoTextViewRegion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                val selectedText = s.toString().trim()
                if (selectedText.isNotEmpty() && region.contains(selectedText)) {
                    mMap.clear()
                    regionDisaster = getRegionCode(selectedText)
                    viewModel.getReportLive(regionDisaster,typeDisaster)
                }
            }
        })
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
        adapter.onItemClick = {
            mMap.clear()
            typeDisaster = it.title.lowercase()
            viewModel.getReportLive(regionDisaster, typeDisaster)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-3.5345913, 114.8801302), 0f))
        setMapStyle()

        viewModel.getReportLive(null,null)

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

    private fun getRegionCode(region: String): String {
        return when (region) {
            "Aceh" -> "ID-AC"
            "Bali" -> "ID-BA"
            "Kep Bangka Belitung" -> "ID-BB"
            "Banten" -> "ID-BT"
            "Bengkulu" -> "ID-BE"
            "Jawa Tengah" -> "ID-JT"
            "Kalimantan Tengah" -> "ID-KT"
            "Sulawesi Tengah" -> "ID-ST"
            "Jawa Timur" -> "ID-JI"
            "Kalimantan Timur" -> "ID-KI"
            "Nusa Tenggara Timur" -> "ID-NT"
            "Gorontalo" -> "ID-GO"
            "DKI Jakarta" -> "ID-JK"
            "Jambi" -> "ID-JA"
            "Lampung" -> "ID-LA"
            "Maluku" -> "ID-MA"
            "Kalimantan Utara" -> "ID-KU"
            "Maluku Utara" -> "ID-MU"
            "Sulawesi Utara" -> "ID-SA"
            "Sumatera Utara" -> "ID-SU"
            "Papua" -> "ID-PA"
            "Riau" -> "ID-RI"
            "Kep Riau" -> "ID-KR"
            "Sulawesi Tenggara" -> "ID-SG"
            "Kalimantan Selatan" -> "ID-KS"
            "Sulawesi Selatan" -> "ID-SN"
            "Sumatera Selatan" -> "ID-SS"
            "DI Yogyakarta" -> "ID-YO"
            "Jawa Barat" -> "ID-JB"
            "Kalimantan Barat" -> "ID-KB"
            "Nusa Tenggara Barat" -> "ID-NB"
            "Papua Barat" -> "ID-PB"
            "Sulawesi Barat" -> "ID-SR"
            "Sumatera Barat" -> "ID-SB"
            else -> "ID-JK"
        }
    }

}