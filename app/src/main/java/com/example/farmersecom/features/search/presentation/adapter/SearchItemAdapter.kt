package com.example.farmersecom.features.search.presentation.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutSearchItemViewBinding
import com.example.farmersecom.features.search.domain.model.SearchItem



class SearchItemAdapter (diffUtil: DiffUtil.ItemCallback<SearchItem>)
    : PagingDataAdapter<SearchItem, SearchItemAdapter.ViewHolder>(diffUtil)
{



    inner  class  ViewHolder(binding: LayoutSearchItemViewBinding) : RecyclerView.ViewHolder(binding.root)
    {

        fun bind(searchItem: SearchItem?)
        {

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutSearchItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val searchItem = getItem(position)
        holder.bind(searchItem)
    }


}