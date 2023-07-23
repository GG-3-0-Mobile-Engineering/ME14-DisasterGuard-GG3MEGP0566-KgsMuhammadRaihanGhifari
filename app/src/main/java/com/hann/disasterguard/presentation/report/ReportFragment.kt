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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hann.disasterguard.R
import com.hann.disasterguard.coreapp.ui.ReportAdapter
import com.hann.disasterguard.databinding.FragmentReportBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReportFragment : BottomSheetDialogFragment()  {

    private lateinit var binding: FragmentReportBinding
    private lateinit var adapter: ReportAdapter
    private val viewModel: ReportViewModel by viewModel()
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
                val layoutParams = it.layoutParams as ViewGroup.LayoutParams
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                it.layoutParams = layoutParams

                bottomSheetBehavior = BottomSheetBehavior.from(it)
                bottomSheet.minimumHeight = 350

                bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(view: View, newState: Int) {
                        when (newState) {
                            BottomSheetBehavior.STATE_COLLAPSED -> Log.d(TAG, "Collapsed")
                            BottomSheetBehavior.STATE_DRAGGING -> Log.d(TAG, "Dragging")
                            BottomSheetBehavior.STATE_EXPANDED -> Log.d(TAG, "Expanded")
                            BottomSheetBehavior.STATE_HALF_EXPANDED -> Log.d(TAG, "Half Expanded")
                            BottomSheetBehavior.STATE_HIDDEN -> {
                                Log.d(TAG, "Hidden")
                                dismiss()
                            }
                            BottomSheetBehavior.STATE_SETTLING -> Log.d(TAG, "Settling")
                        }
                    }

                    override fun onSlide(view: View, v: Float) {
                        // Do nothing for the slide event
                    }
                })
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