package com.hann.disasterguard.presentation.map

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.hann.disasterguard.R
import com.hann.disasterguard.coreapp.domain.model.DisasterType
import com.hann.disasterguard.coreapp.ui.DisasterTypeAdapter
import com.hann.disasterguard.coreapp.utils.Utils
import com.hann.disasterguard.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding :FragmentMapBinding
    private lateinit var mMap : GoogleMap
    private lateinit var adapter: DisasterTypeAdapter
    private val viewModel: MapViewModel by viewModels()
    private  var typeDisaster : String? = null
    private  var regionDisaster : String? = null
    private var statusMap : Boolean = false
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        dialog = Dialog(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = Dialog(requireContext())
        initRecyclerView()
        initListRegion()
        getListType()

        binding.apply {
            btnSettings.setOnClickListener {
                startActivity(
                    Intent(requireContext(), Class.forName("com.hann.disasterguard.settings.SettingActivity"))
                )
            }
            btnFilter.setOnClickListener {
                Utils.showPeriodeDisasterDialog(dialog)
            }
        }
    }

    private fun initListRegion() {
        val region = resources.getStringArray(R.array.province)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, region)
        binding.lvArea.adapter = adapter

        binding.apply {
            svSearchLocation.setOnCloseListener{
                binding.btnSettings.visibility = View.VISIBLE
                binding.btnFilter.visibility = View.VISIBLE
                true
            }
            svSearchLocation.setOnSearchClickListener {
                binding.btnSettings.visibility = View.GONE
                binding.btnFilter.visibility = View.GONE
            }
        }

        binding.svSearchLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (region.contains(query)){
                    mMap.clear()
                    regionDisaster = query?.let { getRegionCode(it) }
                    viewModel.getReportLive(regionDisaster,typeDisaster)
                }else{
                    Toast.makeText(requireContext(), "Masukan Area", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    binding.cvListArea.visibility = View.GONE
                    binding.btnSettings.visibility = View.VISIBLE
                    binding.btnFilter.visibility = View.VISIBLE

                } else if (newText.isNotEmpty()) {
                    binding.cvListArea.visibility = View.VISIBLE
                    binding.btnSettings.visibility = View.GONE
                    binding.btnFilter.visibility = View.GONE
                    adapter.filter.filter(newText)

                    binding.lvArea.onItemClickListener =
                        AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                            val selectedItem = adapterView.getItemAtPosition(position) as String
                            binding.svSearchLocation.setQuery(selectedItem, true)
                            binding.cvListArea.visibility = View.GONE
                            binding.btnSettings.visibility = View.VISIBLE
                            binding.btnFilter.visibility = View.VISIBLE
                            mMap.clear()
                            regionDisaster = getRegionCode(selectedItem)
                            viewModel.getReportLive(regionDisaster,typeDisaster)
                        }
                }
                return false
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
        statusMap = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-3.5345913, 114.8801302), 0f))
        setMapStyle()

        viewModel.getReportLive(null,null)

        viewModel.state.observe(viewLifecycleOwner){
            if (it.isLoading){
                Utils.showLoading(dialog)
            }
            if (it.error.isNotBlank()){
                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
            }
            if (it.map.isNotEmpty()){
                for (i in it.map){
                    var markerColour = 0f
                    when(i.properties.disaster_type){
                        "flood" -> markerColour = BitmapDescriptorFactory.HUE_AZURE
                        "earthquake" -> markerColour = BitmapDescriptorFactory.HUE_ORANGE
                        "fire" -> markerColour = BitmapDescriptorFactory.HUE_RED
                        "haze" -> markerColour = BitmapDescriptorFactory.HUE_VIOLET
                        "wind" -> markerColour = BitmapDescriptorFactory.HUE_CYAN
                        "volcano" -> markerColour = BitmapDescriptorFactory.HUE_YELLOW
                    }
                    mMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(i.coordinates[1], i.coordinates[0]))
                            .title(i.properties.disaster_type)
                            .snippet(i.properties.text)
                            .icon(BitmapDescriptorFactory.defaultMarker(markerColour))
                            .alpha(0.8f)
                    )
                }
            }
            if (!it.isLoading){
                Utils.hideLoading(dialog)
            }
        }
    }

    private fun setMapStyle() {
        try {
            val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val shouldNotify = preferences.getBoolean(requireContext().getString(R.string.pref_key_theme), false)
            if (shouldNotify){
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style_night))
            }else{
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
            }

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