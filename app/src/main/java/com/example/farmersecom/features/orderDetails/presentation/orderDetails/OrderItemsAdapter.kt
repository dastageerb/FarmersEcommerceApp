package com.example.farmersecom.features.orderDetails.presentation.orderDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutOrderItemsBinding
import com.example.farmersecom.databinding.LayoutStoreDashboardListItemBinding
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.features.storeAdmin.domain.model.DashBoardItem
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.load

class OrderItemsAdapter : androidx.recyclerview.widget.ListAdapter<ProductDetailsResponse,OrderItemsAdapter.ViewHolder>
    (
    object : DiffUtil.ItemCallback<ProductDetailsResponse>()
    {
        override fun areItemsTheSame(oldItem: ProductDetailsResponse, newItem: ProductDetailsResponse): Boolean
        {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProductDetailsResponse, newItem: ProductDetailsResponse): Boolean
        {
            return oldItem.toString() == newItem.toString()
        }
    }
)
{

    inner class ViewHolder(private val binding: LayoutOrderItemsBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data:ProductDetailsResponse)
        {
            binding.imageViewLayoutOrderItemsProductImage.load(data.productPictures!![0].img)
            binding.textViewLayoutOrderItemsProductName.text = data.productName
            binding.textViewLayoutOrderItemsItemProductPrice.text = data.productPrice.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view  = LayoutOrderItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(getItem(position))
    } // onBindViewHolder closed

} // ToDOListAdapter class closed