package com.example.farmersecom.features.cart.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutCartItemViewBinding
import com.example.farmersecom.features.cart.domain.CartItem

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




        inner class  ViewHolder(view: LayoutCartItemViewBinding) : RecyclerView.ViewHolder(view.root)
        {
            fun bind()
            {

            }

        } // viewHolder closed

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        {
            val binding = LayoutCartItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(binding);
        } //

        override fun onBindViewHolder(holder: ViewHolder, position: Int)
        {
           holder.bind()
        }


    } // CartItemAdapter