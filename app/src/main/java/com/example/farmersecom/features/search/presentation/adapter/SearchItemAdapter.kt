package com.example.farmersecom.features.search.presentation.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutSearchItemViewBinding
import com.example.farmersecom.features.home.domain.model.more.MoreProduct
import com.example.farmersecom.features.search.domain.model.Product
import com.example.farmersecom.features.search.domain.model.SearchResponse
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load


class SearchItemAdapter (val onStoreItemClicked:(productId:String)->Unit)
    : ListAdapter<Product, SearchItemAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Product>()
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



    inner  class  ViewHolder(val binding: LayoutSearchItemViewBinding) : RecyclerView.ViewHolder(binding.root)
    {

        fun bind(product: Product?)
        {
            binding.apply()
            {
                if (product?.productPicture?.isNotEmpty() == true)
                {
                    layoutSearchItemsImageView.load(product?.productPicture)
                }
                layoutSearchItemsProductNameTextView.text = product?.productName
                layoutSearchItemsProductCategoryTextView.text = product?.productCategory
                layoutSearchItemsProductPriceTextView.text = product?.productPrice.toString()
                layoutSearchItemsProductLocationTextView.text = product?.productLocation
//
            }
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


        holder.itemView.setOnClickListener()
        {
            onStoreItemClicked(searchItem.productId!!)
        } //

    }


}