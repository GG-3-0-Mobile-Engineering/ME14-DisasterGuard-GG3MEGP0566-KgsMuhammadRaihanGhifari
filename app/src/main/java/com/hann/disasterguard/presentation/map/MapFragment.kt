package com.hann.disasterguard.presentation.map

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.datepicker.MaterialDatePicker
import com.hann.disasterguard.R
import com.hann.disasterguard.coreapp.domain.model.ArchiveReport
import com.hann.disasterguard.coreapp.domain.model.DisasterType
import com.hann.disasterguard.coreapp.domain.model.GeometryReport
import com.hann.disasterguard.coreapp.ui.DisasterTypeAdapter
import com.hann.disasterguard.coreapp.ui.ReportAdapter
import com.hann.disasterguard.coreapp.utils.DateFormatter
import com.hann.disasterguard.coreapp.utils.Utils
import com.hann.disasterguard.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding :FragmentMapBinding
    private lateinit var mMap : GoogleMap
    private lateinit var adapter: DisasterTypeAdapter
    private lateinit var reportAdapter: ReportAdapter
    private val viewModel: MapViewModel by viewModels()
    private  var typeDisaster : String? = null
    private  var regionDisaster : String? = null
    private var statusMap : Boolean = false
    private lateinit var dialog: Dialog
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var arrowImageView: ImageView
    private lateinit var mBottomSheetLayout: LinearLayout
    private lateinit var startDate : String
    private lateinit var endDate : String
    private val boundsBuilder = LatLngBounds.Builder()

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



        dialog = Dialog(requireContext())
        bottomSheet()
        initRecyclerView()
        initListRegion()
        getListType()

        binding.apply {
            btnSettings.setOnClickListener {
                startActivity(
                    Intent(requireContext(), Class.forName("com.hann.disasterguard.settings.SettingActivity"))
                )
            }
            btnDate.setOnClickListener {
                startDate()
            }
        }
    }

    private fun startDate() {
        Utils.toastInfo(requireActivity(), "Start Date", "Please Enter Start Date")
        val datePicker= MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.enter_start_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setPositiveButtonText(getString(R.string.enter_start))
            .build()

        datePicker.addOnPositiveButtonClickListener {
            startDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(
                Date(it)
            )
            endDate()
        }
        datePicker.show(parentFragmentManager, "tag")
    }

    private fun endDate(){
        Utils.toastInfo(requireActivity(), "End Date", "Please Enter End Date")
        val datePicker= MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.enter_end_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setPositiveButtonText(getString(R.string.find_archive))
            .build()
        datePicker.addOnPositiveButtonClickListener {
            endDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(
                Date(it)
            )
            getDataArchive()
        }
        datePicker.show(parentFragmentManager, "tag")
    }

    private fun getDataArchive() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        val date1 = dateFormat.parse(startDate)
        val date2 = dateFormat.parse(endDate)

        val comparison = date1?.compareTo(date2)

        if (comparison != null) {
            when {
                comparison < 0 -> {
                    mMap.clear()
                    viewModel.getArchiveReport(startDate, endDate, geoformat = "geojson")
                }
                comparison == 0 -> Utils.toastInfo(requireActivity(), "Equals Date", "Date cannot be the same")
                else ->Utils.toastFailed(requireActivity(), "Failed Date", "Start date value cannot be greater than date end")
            }
        }

        viewModel.stateArchive.observe(this){
            if (it.isLoading){
                Utils.showLoading(dialog)
                binding.cvMarkerItem.visibility = View.GONE
            }
            if (it.error.isNotBlank()){
                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
            }
            if (it.archive.isNotEmpty()){
                for (i in it.archive){
                    var markerColour = 0f
                    when(i.properties.disaster_type){
                        "flood" -> markerColour = BitmapDescriptorFactory.HUE_AZURE
                        "earthquake" -> markerColour = BitmapDescriptorFactory.HUE_ORANGE
                        "fire" -> markerColour = BitmapDescriptorFactory.HUE_RED
                        "haze" -> markerColour = BitmapDescriptorFactory.HUE_VIOLET
                        "wind" -> markerColour = BitmapDescriptorFactory.HUE_CYAN
                        "volcano" -> markerColour = BitmapDescriptorFactory.HUE_YELLOW
                    }


                    val marker =  mMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(i.geometry.coordinates[1], i.geometry.coordinates[0]))
                            .title(i.properties.disaster_type)
                            .snippet(i.properties.text)
                            .icon(BitmapDescriptorFactory.defaultMarker(markerColour))
                            .alpha(0.8f)
                    )

                    boundsBuilder.include(LatLng(i.geometry.coordinates[1], i.geometry.coordinates[0]))
                    val bounds: LatLngBounds = boundsBuilder.build()
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            resources.displayMetrics.widthPixels,
                            resources.displayMetrics.heightPixels,
                            300
                        )
                    )
                    marker?.tag = i

                    mMap.setOnMarkerClickListener { clickedMarker ->
                        if (clickedMarker.tag != null){
                            val data = clickedMarker.tag as ArchiveReport
                            setDataMarker(data)
                        }
                        true
                    }
                }
            }
            if (!it.isLoading){
                Utils.hideLoading(dialog)
            }
        }

    }

    private fun setDataMarker(archive : ArchiveReport) {
           binding.cvMarkerItem.visibility = View.VISIBLE
           if (archive.properties.image_url != null ) {
               Glide.with(requireContext())
                   .load(archive.properties.image_url)
                   .placeholder(com.hann.disasterguard.coreapp.R.drawable.placeholder)
                   .into(binding.ivDisasterMarker)
           } else {
               val placeholderDrawable = ContextCompat.getDrawable(
                   requireContext(),
                   getCategoryDisaster(archive.properties.disaster_type)
               )
               binding.ivDisasterMarker.setImageDrawable(placeholderDrawable)
           }
           if (archive.properties.title != null){
               binding.tvTitleDisasterMarker.text = archive.properties.title.toString()
           }else{
               binding.tvTitleDisasterMarker.text = requireActivity().getString(com.hann.disasterguard.coreapp.R.string.empty_title)
           }
           binding.cvDisasterTypeMarker.background = getTypeDisaster(archive.properties.disaster_type, requireView())
           binding.tvTypeDisasterMarker.text = archive.properties.disaster_type
           binding.tvDateDisasterMarker.text = DateFormatter.formatDate(archive.properties.created_at)

    }

    private fun getTypeDisaster(disasterType: String, view: View): Drawable? {
        return when (disasterType) {
            "flood" -> ContextCompat.getDrawable(view.context, com.hann.disasterguard.coreapp.R.color.azure)
            "earthquake" -> ContextCompat.getDrawable(view.context, com.hann.disasterguard.coreapp.R.color.orange)
            "fire" -> ContextCompat.getDrawable(view.context, com.hann.disasterguard.coreapp.R.color.red)
            "haze" ->ContextCompat.getDrawable(view.context, com.hann.disasterguard.coreapp.R.color.violet)
            "wind" -> ContextCompat.getDrawable(view.context, com.hann.disasterguard.coreapp.R.color.cyan)
            "volcano" ->ContextCompat.getDrawable(view.context, com.hann.disasterguard.coreapp.R.color.yellow)
            else -> ContextCompat.getDrawable(view.context, com.hann.disasterguard.coreapp.R.color.black)
        }
    }

    private fun getCategoryDisaster(disasterType: String): Int {
        return when (disasterType) {
            "flood" -> com.hann.disasterguard.coreapp.R.drawable.flood
            "earthquake" -> com.hann.disasterguard.coreapp.R.drawable.earthquake
            "fire" -> com.hann.disasterguard.coreapp.R.drawable.fire
            "haze" -> com.hann.disasterguard.coreapp.R.drawable.haze
            "wind" -> com.hann.disasterguard.coreapp.R.drawable.wind
            "volcano" -> com.hann.disasterguard.coreapp.R.drawable.volcano
            else -> com.hann.disasterguard.coreapp.R.drawable.flood
        }
    }



    private fun bottomSheet() {
        mBottomSheetLayout = binding.sheet.bottomSheetLayout
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout)
        arrowImageView =  binding.sheet.bottomSheetArrow

        // Arrow image changes its state to expanded and collapsed
        arrowImageView.setOnClickListener {
            if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        // When we slide, it rotates the image to 180 degrees,
        // if it's downside it rotates to upside else vice-versa
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                arrowImageView.rotation = slideOffset * 180
            }
        })
    }

    private fun initListRegion() {
        val region = resources.getStringArray(R.array.province)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, region)
        binding.lvArea.adapter = adapter

        binding.apply {
            svSearchLocation.setOnCloseListener{
                binding.btnSettings.visibility = View.VISIBLE
                binding.btnDate.visibility = View.VISIBLE
                true
            }
            svSearchLocation.setOnSearchClickListener {
                binding.btnSettings.visibility = View.GONE
                binding.btnDate.visibility = View.GONE
            }
        }

        binding.svSearchLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (region.contains(query)){
                    mMap.clear()
                    regionDisaster = query?.let { Utils.getRegionCode(it) }
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
                    binding.btnDate.visibility = View.VISIBLE

                } else if (newText.isNotEmpty()) {
                    binding.cvListArea.visibility = View.VISIBLE
                    binding.btnSettings.visibility = View.GONE
                    binding.btnDate.visibility = View.GONE
                    adapter.filter.filter(newText)

                    binding.lvArea.onItemClickListener =
                        AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                            val selectedItem = adapterView.getItemAtPosition(position) as String
                            binding.svSearchLocation.setQuery(selectedItem, true)
                            binding.cvListArea.visibility = View.GONE
                            binding.btnSettings.visibility = View.VISIBLE
                            binding.btnDate.visibility = View.VISIBLE
                            mMap.clear()
                            regionDisaster = Utils.getRegionCode(selectedItem)
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

        reportAdapter = ReportAdapter()
        binding.sheet.rvDisasterReport.layoutManager = LinearLayoutManager(requireContext())
        binding.sheet.rvDisasterReport.adapter = reportAdapter
        binding.sheet.rvDisasterReport.setHasFixedSize(false)
        reportAdapter.onItemClick = {

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
                binding.sheet.shimmerLayoutFollow.visibility = View.VISIBLE
                binding.cvMarkerItem.visibility = View.GONE
            }
            if (it.error.isNotBlank()){
                binding.sheet.shimmerLayoutFollow.visibility = View.GONE
                binding.sheet.viewErrorFollow.tvError.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
            }
            if (it.map.isNotEmpty()){
                reportAdapter.setData(it.map)
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
                    val marker =  mMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(i.coordinates[1], i.coordinates[0]))
                            .title(i.properties.disaster_type)
                            .snippet(i.properties.text)
                            .icon(BitmapDescriptorFactory.defaultMarker(markerColour))
                            .alpha(0.8f)
                    )

                    boundsBuilder.include(LatLng(i.coordinates[1], i.coordinates[0]))

                    val bounds: LatLngBounds = boundsBuilder.build()
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            resources.displayMetrics.widthPixels,
                            resources.displayMetrics.heightPixels,
                            300
                        )
                    )

                    marker?.tag = i

                    mMap.setOnMarkerClickListener { clickedMarker ->
                        if (clickedMarker.tag != null){
                            val data = clickedMarker.tag as GeometryReport
                            setDataMarkerReport(data)
                        }
                        true
                    }
                }
            }
            if (!it.isLoading){
                binding.sheet.shimmerLayoutFollow.visibility = View.GONE
                binding.sheet.viewErrorFollow.tvError.visibility = View.GONE
                Utils.hideLoading(dialog)
            }
        }
    }

    private fun setDataMarkerReport(data: GeometryReport) {
        binding.cvMarkerItem.visibility = View.VISIBLE
        if (data.properties.image_url != null ) {
            Glide.with(requireContext())
                .load(data.properties.image_url)
                .placeholder(com.hann.disasterguard.coreapp.R.drawable.placeholder)
                .into(binding.ivDisasterMarker)
        } else {
            val placeholderDrawable = ContextCompat.getDrawable(
                requireContext(),
                getCategoryDisaster(data.properties.disaster_type)
            )
            binding.ivDisasterMarker.setImageDrawable(placeholderDrawable)
        }
        if (data.properties.title != null){
            binding.tvTitleDisasterMarker.text = data.properties.title
        }else{
            binding.tvTitleDisasterMarker.text = requireActivity().getString(com.hann.disasterguard.coreapp.R.string.empty_title)
        }
        binding.cvDisasterTypeMarker.background = getTypeDisaster(data.properties.disaster_type, requireView())
        binding.tvTypeDisasterMarker.text = data.properties.disaster_type
        binding.tvDateDisasterMarker.text = DateFormatter.formatDate(data.properties.created_at)
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

}