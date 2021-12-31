package com.example.farmersecom.features.storeAdmin.presentation

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.databinding.LayoutProductStatusItemBinding
import com.example.farmersecom.features.storeAdmin.domain.model.productStatusResponse.ProductStatus
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import timber.log.Timber

class ProductStatusAdapter(private val context:Context,private val onProductClicked:(String)->Unit) : ListAdapter<ProductStatus, ProductStatusAdapter.ViewHolder>
    (object : DiffUtil.ItemCallback<ProductStatus>()
{
    override fun areItemsTheSame(oldItem: ProductStatus, newItem: ProductStatus): Boolean
    {
        return oldItem == newItem
    }


    override fun areContentsTheSame(oldItem: ProductStatus, newItem: ProductStatus): Boolean
    {
        return oldItem.toString() == newItem.toString()
    }
})
{


    inner class ViewHolder(private val binding: LayoutProductStatusItemBinding) : RecyclerView.ViewHolder(binding.root)
    {

        fun bind(product:ProductStatus?, position: Int)
        {
            when(product?.isActive)
            {
                true ->
                {
                    binding.layoutProductStatusItemChangeProductStatusButton.text = context.getString(R.string.discontinue)
                    binding.layoutProductStatusIndicatorCardView.setCardBackgroundColor(Color.GREEN)
                    binding.layoutProductStatusItemDeleteProductButton.hide()
                    binding.layoutProductStatusItemEditProductButton.hide()
                }
                false ->
                {
                    binding.layoutProductStatusItemChangeProductStatusButton.text = context.getString(R.string.active)
                    binding.layoutProductStatusIndicatorCardView.setCardBackgroundColor(Color.RED)
                    binding.layoutProductStatusItemDeleteProductButton.show()
                }
            } // when closed

            binding.layoutActiveProductItemProductNameTextView.text = product?.productName
            binding.layoutActiveProductItemProductPriceTextView.text = product?.price.toString()
            if(!product?.productPictures.isNullOrEmpty())
            {
                binding.layoutProductStatusItemImageView.load(product?.productPictures?.get(0)?.img)
            }


            binding.layoutProductStatusItemChangeProductStatusButton.setOnClickListener()
            {

                onChanStatusListener?.let { it1 -> it1(!product?.isActive!!,position, product.id!!) }
            }

            binding.layoutProductStatusItemDeleteProductButton.setOnClickListener()
            {
                onDeleteProductListener?.let { it1 -> it1(position,product?.id!!) }
            }

            binding.layoutProductStatusItemEditProductButton.setOnClickListener()
            {
                onEditProductListener?.let { it1 -> it1(product?.id!!) }
            }



        } // bind closed

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view =
            LayoutProductStatusItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(getItem(position),position)

        holder.itemView.setOnClickListener()
        {
      //      getItem(position).id?.let { it1 -> onOrderClicked(it1) }
        }
    } // onBindViewHolder closed


    // higher Order functions
        private var onChanStatusListener:((status:Boolean,position:Int,productId:String)->Unit)? =null

    fun onChangeStatusClick(listener:(status:Boolean,position:Int,productId:String) -> Unit)
        {
            onChanStatusListener = listener
        }

    private var onDeleteProductListener:((position:Int,productId:String)->Unit)? =null
    fun onDeleteProductClicked(listener:(position:Int,productId:String)->Unit)
    {
        onDeleteProductListener = listener
    }


    private var onEditProductListener:((productId:String)->Unit)? =null
    fun onEditProductClicked(listener:(productId:String)->Unit)
    {
        onEditProductListener= listener
    }



} // adapter closed
