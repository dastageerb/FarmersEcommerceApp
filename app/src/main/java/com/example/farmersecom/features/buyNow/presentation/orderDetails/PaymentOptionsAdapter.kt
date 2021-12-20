package com.example.farmersecom.features.buyNow.presentation.orderDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutPaymentOptionsItemBinding

class PaymentOptionsAdapter(val paymentOptionSelected:(String)->Unit) : androidx.recyclerview.widget.ListAdapter<String,PaymentOptionsAdapter.ViewHolder>
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

        holder.itemView.setOnClickListener()
        {
            paymentOptionSelected(getItem(position))
        }

    } // onBindViewHolder closed

} // ToDOListAdapter class closed