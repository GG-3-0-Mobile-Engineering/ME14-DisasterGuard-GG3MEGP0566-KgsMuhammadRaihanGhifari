package com.hann.disasterguard.coreapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hann.disasterguard.coreapp.R
import com.hann.disasterguard.coreapp.databinding.ItemLayoutReportBinding
import com.hann.disasterguard.coreapp.domain.model.GeometryReport
import java.util.ArrayList

class ReportAdapter : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    private var listData = ArrayList<GeometryReport>()
    var onItemClick: ((GeometryReport) -> Unit)? = null

    fun setData(newListData: List<GeometryReport>?) {
        if (newListData == null) return
        val diffResult = DiffUtil.calculateDiff(MyDiffUtil(listData, newListData))
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout_report, parent, false))
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemLayoutReportBinding.bind(itemView)

        fun bind(data: GeometryReport) {
            with(binding){
                Glide.with(itemView.context)
                    .load(data.properties.image_url)
                    .into(itemReportImage)
                itemReportTitle.text = data.properties.disaster_type
                itemReportDate.text = data.properties.created_at
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

    }

    class MyDiffUtil(private val oldList: List<GeometryReport>, private val newList: List<GeometryReport>): DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].properties.pkey == newList[newItemPosition].properties.pkey
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}