package com.example.farmersecom.features.home.presentation.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutHomeLatestProductsItemBinding
import com.example.farmersecom.features.home.domain.model.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.sharedModel.CategoryItem

class HomeLatestProductsAdapter(val context:Context,val onSeeAllClicked:(CategoryItem)->Unit):ListAdapter<HomeLatestItem,HomeLatestProductsAdapter.ViewHolder>(object : DiffUtil.ItemCallback<HomeLatestItem>()
{
    override fun areItemsTheSame(oldItem: HomeLatestItem, newItem: HomeLatestItem): Boolean
    {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HomeLatestItem, newItem: HomeLatestItem): Boolean
    {
        return oldItem.toString() == newItem.toString()
    }
})
{


    inner class  ViewHolder(private val binding: LayoutHomeLatestProductsItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(item: HomeLatestItem)
        {
            val adapter = HomeLatestChildAdapter()
            {
                id ->
                onChildRecyclerClicked?.let { it(id) }
            }
            binding.layoutHomeLatestProductsChildRecyclerView.setHasFixedSize(true)
            binding.layoutHomeLatestProductsChildRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
            binding.layoutHomeLatestProductsChildRecyclerView.adapter = adapter
            adapter.submitList(item.products)
            binding.layoutHomeLatestProductsItemCategoryNameTextView.text = item.categoryName

            binding.layoutHomeLatestProductsItemSeeAllTextView.setOnClickListener()
            {
               onSeeAllClicked(CategoryItem(item.categoryId,item.categoryName))
            }

        } // bind closed
    } // viewHolder closed


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding = LayoutHomeLatestProductsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding);
    } //

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(getItem(position))
    }


    private var onChildRecyclerClicked : ((productId:String) -> Unit)? = null
    fun setOnChildClickListener(listener: (productId:String)->Unit)
    {
        onChildRecyclerClicked = listener
    }


}