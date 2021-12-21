package com.example.farmersecom.features.buyerSection.presentation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutOrderStatusItemsBinding
import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.Order
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load

class OrderStatusAdapter(private val onOrderClicked:(String)->Unit) : ListAdapter<Order, OrderStatusAdapter.ViewHolder>
    (object : DiffUtil.ItemCallback<Order>()
{
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean
    {
        return oldItem == newItem
    }


    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean
    {
        return oldItem.toString() == newItem.toString()
    }
})
{


    inner class ViewHolder(private val binding: LayoutOrderStatusItemsBinding) : RecyclerView.ViewHolder(binding.root)
    {

        fun bind(order: Order?)
        {

            binding.layoutOrderStatusProductNameTextView.text = order?.productName
            binding.layoutOrderStatusImageView.load(order?.productImage)

            binding.layoutOrderStatusOrderQuantityTextView.text = order?.orderQuantity.toString()
            binding.layoutOrderStatusOrderStatusTextView.text = order?.orderStatus.toString()
            binding.layoutOrderStatusTotalPriceTextView.text = order?.totalPrice.toString()

            when(order?.orderStatus)
            {
                "completed" -> binding.layoutOrderStatusOrderStatusTextView.setTextColor(Color.GREEN)
                else -> binding.layoutOrderStatusOrderStatusTextView.setTextColor(Color.YELLOW)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view =
            LayoutOrderStatusItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener()
        {
            getItem(position).id?.let { it1 -> onOrderClicked(it1) }
        }


    } // onBindViewHolder closed
}
