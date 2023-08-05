package com.hann.disasterguard.presentation.report

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hann.disasterguard.R
import com.hann.disasterguard.coreapp.ui.ReportAdapter
import com.hann.disasterguard.databinding.FragmentReportBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReportFragment : BottomSheetDialogFragment()  {

    private lateinit var binding: FragmentReportBinding
    private lateinit var adapter: ReportAdapter
    private val viewModel: ReportViewModel by viewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        viewModel.state.observe(viewLifecycleOwner){
        if (it.isLoading){
                binding.shimmerLayoutFollow.startShimmer()
                binding.shimmerLayoutFollow.visibility = View.VISIBLE
            }
            if (it.error.isNotBlank()){
                binding.shimmerLayoutFollow.visibility = View.GONE
                binding.viewErrorFollow.root.visibility = View.VISIBLE
                binding.viewErrorFollow.tvError.text = it.error
            }
            if (it.report.isNotEmpty()){
                binding.shimmerLayoutFollow.stopShimmer()
                binding.viewErrorFollow.root.visibility = View.GONE
                binding.shimmerLayoutFollow.visibility = View.GONE
                adapter.setData(it.report)
            }
            if (it.report.isEmpty()){
                binding.shimmerLayoutFollow.stopShimmer()
                binding.shimmerLayoutFollow.visibility = View.GONE
                binding.viewErrorFollow.root.visibility = View.VISIBLE
                binding.viewErrorFollow.tvError.text = getString(R.string.empty_recent_disaster)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogInterface: DialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val originHeight = (0.3f * resources.displayMetrics.heightPixels).toInt()
                val layoutParams = it.layoutParams as ViewGroup.LayoutParams
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                it.layoutParams = layoutParams

                bottomSheetBehavior = BottomSheetBehavior.from(it)
                bottomSheetBehavior.isHideable = false
                bottomSheetBehavior.peekHeight = originHeight

                bottomSheetBehavior.apply {
                    state = BottomSheetBehavior.STATE_COLLAPSED
                    addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                        override fun onStateChanged(view: View, newState: Int) {
                            when (newState) {
                                BottomSheetBehavior.STATE_COLLAPSED -> Log.d(TAG, "Collapsed")
                                BottomSheetBehavior.STATE_DRAGGING -> Log.d(TAG, "Dragging")
                                BottomSheetBehavior.STATE_EXPANDED -> binding.slideDownBtn.visibility = View.VISIBLE
                                BottomSheetBehavior.STATE_HALF_EXPANDED -> Log.d(TAG, "Half Expanded")
                                BottomSheetBehavior.STATE_HIDDEN -> {
                                    Log.d(TAG, "Hidden")
                                }
                                BottomSheetBehavior.STATE_SETTLING -> Log.d(TAG, "Settling")
                            }
                        }

                        override fun onSlide(view: View, v: Float) {
                            if (v > originHeight) {
                                state = BottomSheetBehavior.STATE_EXPANDED
                            }
                        }
                    })
                }
            }
        }

        return dialog
    }

    private fun initRecyclerView() {
        adapter = ReportAdapter()
        binding.rvDisasterReport.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDisasterReport.adapter = adapter
        binding.rvDisasterReport.setHasFixedSize(false)
    }

}