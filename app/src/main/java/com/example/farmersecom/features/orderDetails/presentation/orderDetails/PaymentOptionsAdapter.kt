package com.example.farmersecom.features.orderDetails.presentation.orderDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutOrderItemsBinding
import com.example.farmersecom.databinding.LayoutPaymentOptionsItemBinding
import com.example.farmersecom.databinding.LayoutStoreDashboardListItemBinding
import com.example.farmersecom.features.search.domain.model.SearchItem
import com.example.farmersecom.features.storeAdmin.domain.model.DashBoardItem

class PaymentOptionsAdapter : androidx.recyclerview.widget.ListAdapter<String,PaymentOptionsAdapter.ViewHolder>
    (
    object : DiffUtil.ItemCallback<String>()
    {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean
        {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean
        {
            return oldItem.toString() == newItem.toString()
        }
    }
)
{

    inner class ViewHolder(val binding: LayoutPaymentOptionsItemBinding)  : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(option: String)
        {
            binding.layoutPaymentOptionsItemOptionName.text = option
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view  = LayoutPaymentOptionsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(getItem(position))
    } // onBindViewHolder closed

} // ToDOListAdapter class closed