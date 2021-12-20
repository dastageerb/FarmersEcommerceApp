package com.example.farmersecom.features.buyNow.presentation.orderDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse

//class OrderItemsAdapter(val onIncreaseClicked:(Int,ProductDetailsResponse)->Unit,val onDecreaseClicked:(Int,ProductDetailsResponse)->Unit) : androidx.recyclerview.widget.ListAdapter<ProductDetailsResponse,OrderItemsAdapter.ViewHolder>
//    (
//    object : DiffUtil.ItemCallback<ProductDetailsResponse>()
//    {
//        override fun areItemsTheSame(oldItem: ProductDetailsResponse, newItem: ProductDetailsResponse): Boolean
//        {
//            return oldItem == newItem
//        }
//
//        override fun areContentsTheSame(oldItem: ProductDetailsResponse, newItem: ProductDetailsResponse): Boolean
//        {
//            return oldItem.toString() == newItem.toString()
//        }
//    }
//)
//{
//
//    inner class ViewHolder(private val binding: LayoutOrderItemsBinding) : RecyclerView.ViewHolder(binding.root)
//    {
//        fun bind(data:ProductDetailsResponse,positon:Int)
//        {
//
//            binding.imageViewLayoutOrderItemsProductImage.load(data.productPictures!![0].img)
//            binding.textViewLayoutOrderItemsProductName.text = data.productName
//            binding.textViewLayoutOrderItemsItemProductPrice.text = data.productPrice.toString()
//
//            binding.layoutOrderItemsQuantityTextView.text =data.productQuantity.toString()
//
//            binding.layoutOrderItemIncreaseQuantityImageView.setOnClickListener()
//            {
//
//
//            }
//
//            binding.layoutOrderItemDecreaseQuantityImageView.setOnClickListener()
//            {
//                onDecreaseClicked(positon,data)
//            }
//
//        } // bind closed
//    } // ViewHolder closed
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
//    {
//        val view  = LayoutOrderItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int)
//    {
//
//        holder.bind(getItem(position),position)
//    } // onBindViewHolder closed

//} // ToDOListAdapter class closed