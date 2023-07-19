package com.hann.disasterguard.presentation.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hann.disasterguard.R
import com.hann.disasterguard.coreapp.ui.ReportAdapter
import com.hann.disasterguard.databinding.FragmentReportBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReportFragment : BottomSheetDialogFragment()  {

    private lateinit var binding: FragmentReportBinding
    private lateinit var adapter: ReportAdapter
    private val viewModel: ReportViewModel by viewModel()

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

    private fun initRecyclerView() {
        adapter = ReportAdapter()
        binding.rvDisasterReport.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDisasterReport.adapter = adapter
        binding.rvDisasterReport.setHasFixedSize(false)
    }

}