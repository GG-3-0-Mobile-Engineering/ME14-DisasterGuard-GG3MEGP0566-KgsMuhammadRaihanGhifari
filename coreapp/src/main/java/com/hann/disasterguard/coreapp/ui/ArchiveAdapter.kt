package com.hann.disasterguard.coreapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hann.disasterguard.coreapp.R
import com.hann.disasterguard.coreapp.databinding.ItemLayoutReportBinding
import com.hann.disasterguard.coreapp.domain.model.ArchiveReport
import com.hann.disasterguard.coreapp.utils.DateFormatter
import java.util.ArrayList


class ArchiveAdapter : RecyclerView.Adapter<ArchiveAdapter.ViewHolder>() {

    private var listData = ArrayList<ArchiveReport>()
    var onItemClick: ((ArchiveReport) -> Unit)? = null

    fun setData(newListData: List<ArchiveReport>?) {
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

        fun bind(data: ArchiveReport) {
            with(binding){
                if (data.properties.image_url != null ) {
                    Glide.with(itemView.context)
                        .load(data.properties.image_url)
                        .placeholder(R.drawable.placeholder)
                        .into(itemReportImage)
                } else {
                    val placeholderDrawable = ContextCompat.getDrawable(
                        itemView.context,
                        getCategoryDisaster(data.properties.disaster_type)
                    )
                    itemReportImage.setImageDrawable(placeholderDrawable)
                }
                itemReportTitle.text = data.properties.disaster_type
                itemReportDate.text = DateFormatter.formatDate(data.properties.created_at)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[bindingAdapterPosition])
            }
        }

    }

    class MyDiffUtil(private val oldList: List<ArchiveReport>, private val newList: List<ArchiveReport>): DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].properties.pkey == newList[newItemPosition].properties.pkey
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    private fun getCategoryDisaster(disasterType: String): Int {
        return when (disasterType) {
            "flood" -> R.drawable.flood
            "earthquake" -> R.drawable.earthquake
            "fire" -> R.drawable.fire
            "haze" ->R.drawable.haze
            "wind" ->R.drawable.wind
            "volcano" ->R.drawable.volcano
            else -> R.drawable.flood
        }
    }
}