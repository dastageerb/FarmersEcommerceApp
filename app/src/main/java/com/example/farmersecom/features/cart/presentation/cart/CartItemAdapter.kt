package com.example.farmersecom.features.cart.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutCartItemViewBinding
import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load

class CartItemAdapter : ListAdapter<CartItem, CartItemAdapter.ViewHolder>
        (object : DiffUtil.ItemCallback<CartItem>()
        {
            override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean
            {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean
            {
                return oldItem.toString() == newItem.toString()
            }
        })
    {




        inner class  ViewHolder(val binding: LayoutCartItemViewBinding) : RecyclerView.ViewHolder(binding.root)
        {
            fun bind(item:CartItem,position: Int)
            {
                binding.imageViewLayoutCartItemProductImage.load(item.productImageUrl)

                binding.textViewLayoutCartItemProductName.text = item.productName
                binding.textViewLayoutCartItemItemProductPrice.text = item.productPrice.toString()
                binding.textViewLayoutCartItemItemUnit.text = item.productQuantityUnit
                binding.LayoutCartItemQuantityTextView.text = item.productQuantity.toString()


                binding.imageViewLayoutCartItemRemoveItem.setOnClickListener()
                {
                    onDeleteCartItem?.let { it1 -> it1(item) }
                }


                binding.layoutOrderItemIncreaseQuantityImageView.setOnClickListener()
                {
                    var quantity = binding.LayoutCartItemQuantityTextView.text.toString().toInt()
                    if(quantity<10)
                    {
                        quantity++
                        binding.LayoutCartItemQuantityTextView.text = quantity.toString()
                        onQuantityChangedListener?.let { it1 -> it1(item.productId,quantity) }
                    } // if closed

                } // onQuantityIncrease click listener closed

                binding.layoutOrderItemDecreaseQuantityImageView.setOnClickListener()
                {

                    var quantity = binding.LayoutCartItemQuantityTextView.text.toString().toInt()
                    if(quantity>1)
                    {
                        quantity--
                        binding.LayoutCartItemQuantityTextView.text = quantity.toString()
                        onQuantityChangedListener?.let { it1 -> it1(item.productId,quantity) }
                    } // if closed


                } // onQuantityDecrease click listener closed

            } // bind closed




        } // viewHolder closed

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        {
            val binding = LayoutCartItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(binding);
        } //

        override fun onBindViewHolder(holder: ViewHolder, position: Int)
        {
           holder.bind(getItem(position),position)
        }


        private var onDeleteCartItem:((cartItem:CartItem)->Unit)? =null

        fun onDeleteCartItemClicked(listener:(item:CartItem)->Unit)
        {
            onDeleteCartItem = listener
        }

        private var onQuantityChangedListener:((productId:String,quantity:Int)->Unit)? =null

        fun onQuantityChanged(listener:(productId:String,quantity:Int)->Unit)
        {
            onQuantityChangedListener = listener
        }





    } // CartItemAdapter