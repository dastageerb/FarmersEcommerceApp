package com.example.farmersecom.features.productStore.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutProductStoreItemBinding
import com.example.farmersecom.features.productStore.domain.model.storeProducts.Product
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load

class  StoreProductsAdapter(val onStoreItemClicked:(productId:String)->Unit)
    : ListAdapter<Product, StoreProductsAdapter.ViewHolder>
    (object : DiffUtil.ItemCallback<Product>()
{
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean
    {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean
    {
        return oldItem.toString() == newItem.toString()
    }
})
{

    inner class ViewHolder(private val binding: LayoutProductStoreItemBinding) : RecyclerView.ViewHolder(binding.root)
    {

        fun bind(product: Product?)
        {
            if(product?.productPictures?.size!! > 0)
            {
                binding.layoutProductStoreItemImageView.load(product?.productPictures!![0].img)
            }

            binding.layoutProductStoreItemProductName.text = product.productName
            binding.layoutProductStoreItemProductPrice.text = product.price.toString()
            binding.layoutProductStoreItemProductLocation.text = product.location

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view =
            LayoutProductStoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener()
        {
            getItem(position).id?.let { it1 -> onStoreItemClicked(it1) }
        }


    } // onBindViewHolder closed
}
