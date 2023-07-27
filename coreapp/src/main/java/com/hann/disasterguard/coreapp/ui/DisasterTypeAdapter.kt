package com.hann.disasterguard.coreapp.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hann.disasterguard.coreapp.R
import com.hann.disasterguard.coreapp.databinding.ItemLayoutDisasterTypeBinding
import com.hann.disasterguard.coreapp.domain.model.DisasterType


class DisasterTypeAdapter : RecyclerView.Adapter<DisasterTypeAdapter.ViewHolder>() {

    private var listData = ArrayList<DisasterType>()
    var onItemClick: ((DisasterType) -> Unit)? = null
    private var selectedItemPosition = -1
    private var lastSelectedPosition = -1

    fun setData(newListData: List<DisasterType>?) {
        if (newListData == null) return
        val diffResult = DiffUtil.calculateDiff(MyDiffUtil(listData, newListData))
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout_disaster_type, parent, false))
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data, position)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {


        private val binding = ItemLayoutDisasterTypeBinding.bind(itemView)

        fun bind(data: DisasterType, position: Int) {
            with(binding){
                itemTypeTitle.text = data.title
            }

            if (selectedItemPosition == position){
                val drawable: Drawable? = ContextCompat.getDrawable(itemView.context, R.color.color_700)
                binding.linearTypeDisaster.background = drawable
            }else{
                val drawable: Drawable? = ContextCompat.getDrawable(itemView.context, R.color.black)
                binding.linearTypeDisaster.background = drawable
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[bindingAdapterPosition])
                lastSelectedPosition = selectedItemPosition
                selectedItemPosition = bindingAdapterPosition
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedItemPosition)
            }

        }

    }

    class MyDiffUtil(private val oldList: List<DisasterType>, private val newList: List<DisasterType>): DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}