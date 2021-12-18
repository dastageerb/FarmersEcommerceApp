package com.example.farmersecom.features.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutMoreItemBinding
import com.example.farmersecom.features.home.domain.model.MoreProductsResponseItem
import com.example.farmersecom.features.home.domain.model.more.MoreProduct
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.load

class MoreItemsPagingAdapter : PagingDataAdapter<MoreProduct,MoreItemsPagingAdapter.ViewHolder>
    (object : DiffUtil.ItemCallback<MoreProduct>()
{
    override fun areItemsTheSame(oldItem: MoreProduct, newItem: MoreProduct): Boolean
    {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MoreProduct, newItem: MoreProduct): Boolean
    {
        return oldItem.toString() == newItem.toString()
    }
})
{


    inner class ViewHolder(private val binding: LayoutMoreItemBinding) : RecyclerView.ViewHolder(binding.root)
    {

        fun bind(product: MoreProduct?)
        {

            binding.apply()
            {
               // if (product?.productPicture?.isNotEmpty() == true)
              //  {
                    layoutMoreItemsImageView.load(product?.productPicture)
              //  }
                layoutMoreItemsProductNameTextView.text = product?.productName
                layoutMoreItemsProductCategoryTextView.text = product?.productCategory
                layoutMoreItemsProductQuantityTextView.text = product?.productQuantity.toString()
                layoutMoreItemsProductQuantityUnitTextView.text = product?.productUnit
                layoutMoreItemsProductPriceTextView.text = product?.productPrice.toString()
                layoutMoreItemsProductLocationTextView.text = product?.productLocation
//
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view =
            LayoutMoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(getItem(position))
    } // onBindViewHolder closed


}

