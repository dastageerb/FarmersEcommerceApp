package com.example.farmersecom.features.home.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutHomeSliderItemBinding
import com.example.farmersecom.features.home.domain.model.sliderModels.HomeSlider
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.load

class HomeSliderAdapter(val onSliderItemSelected: (sliderQuery:String) -> Unit):ListAdapter<HomeSlider, HomeSliderAdapter.ViewHolder>
    (object : DiffUtil.ItemCallback<HomeSlider>()
{
    override fun areItemsTheSame(oldItem: HomeSlider, newItem: HomeSlider): Boolean
    {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HomeSlider, newItem: HomeSlider): Boolean
    {
        return oldItem.toString() == newItem.toString()
    }
})
{


    inner class  ViewHolder(private val view: LayoutHomeSliderItemBinding) : RecyclerView.ViewHolder(view.root)
    {
        fun bind(item: HomeSlider)
        {
            view.layoutHomeSliderImageView.load(item.slideImage)
        }

    } // viewHolder closed


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding = LayoutHomeSliderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding);
    } //

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener()
        {
            onSliderItemSelected(getItem(position).productName)
        }

    } // onBindViewHolder




}