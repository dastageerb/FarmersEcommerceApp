package com.example.farmersecom.features.storeAdmin.presentation.storeDashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.farmersecom.databinding.LayoutStoreDashboardListItemBinding
import com.example.farmersecom.features.storeAdmin.domain.model.DashBoardItem

class StoreDashBoardAdapter(val onClick:(Int)-> Unit) : BaseAdapter()
{

    var itemList = emptyList<DashBoardItem>()
    set(value)
    {
        field=value
    }

    override fun getCount(): Int
    {
       return itemList.size
    }

    override fun getItem(position: Int): Any
    {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long
    {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        val binding: LayoutStoreDashboardListItemBinding
        if (convertView == null) {
            binding = LayoutStoreDashboardListItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as LayoutStoreDashboardListItemBinding
        }

        val item = itemList[position]

        binding.textViewStoreDashboard.text = item.itemName
        binding.imageViewStoreDashBoard.setImageResource(item.imgId)
        binding.storeDashboardCard.setOnClickListener()
        {
            onClick(position)
        }

        return binding.root
    }


} //