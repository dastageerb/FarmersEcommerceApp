package com.example.farmersecom.features.home.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutHomeChildItemBinding
import com.example.farmersecom.features.home.domain.model.homeModels.HomeChildProduct
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load

class HomeLatestChildAdapter(val onProductClicked:(productId:String)->Unit):ListAdapter<HomeChildProduct,HomeLatestChildAdapter.ViewHolder>
    (object : DiffUtil.ItemCallback<HomeChildProduct>()
{
    override fun areItemsTheSame(oldItem: HomeChildProduct, newItem: HomeChildProduct): Boolean
    {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HomeChildProduct, newItem: HomeChildProduct): Boolean
    {
        return oldItem.toString() == newItem.toString()
    }
})
{

    inner class ViewHolder(val binding:LayoutHomeChildItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(item: HomeChildProduct)
        {
            if(item.thumbnail.isNotEmpty())
            {

                binding.layoutHomeChildProductThumbnailImageView.load(item.thumbnail)
            }
            binding.layoutHomeChildProductProductNameTextView.text = item.productName
        }
    } // inner class Closed


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeLatestChildAdapter.ViewHolder
    {
        val binding = LayoutHomeChildItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding);
    } //

    override fun onBindViewHolder(holder: HomeLatestChildAdapter.ViewHolder, position: Int)
    {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener()
        {
            onProductClicked(getItem(position).productId)
        }

    }


} // HomeLatestChildAdapter closed