package com.example.farmersecom.features.productDetails.presentation.productDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutCartItemViewBinding
import com.example.farmersecom.databinding.LayoutProductImagesBinding
import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.features.productDetails.domain.model.ProductPicture
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.load

class ProductImageAdapter : ListAdapter<ProductPicture, ProductImageAdapter.ViewHolder>
    (object : DiffUtil.ItemCallback<ProductPicture>()
{
    override fun areItemsTheSame(oldItem: ProductPicture, newItem: ProductPicture): Boolean
    {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProductPicture, newItem: ProductPicture): Boolean
    {
        return oldItem.toString() == newItem.toString()
    }
})
{




    inner class  ViewHolder(private val view: LayoutProductImagesBinding) : RecyclerView.ViewHolder(view.root)
    {
        fun bind(item:ProductPicture,position: Int)
        {
            view.layoutProductImagesImageView.load(item.img)
            view.layoutProductImagesTextCount.bringToFront()
            
            view.layoutProductImagesTextCount.text = ""+position+1+"/"+itemCount
        }

    } // viewHolder closed

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding = LayoutProductImagesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding);
    } //

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(getItem(position),position)
    }


} // CartItemAdapter