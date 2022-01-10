package com.example.farmersecom.features.communitySection.presentation.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutCommunityPostItemBinding
import com.example.farmersecom.databinding.LayoutMoreItemBinding
import com.example.farmersecom.features.communitySection.domain.models.Post
import com.example.farmersecom.features.home.domain.model.MoreProductsResponseItem
import com.example.farmersecom.features.home.domain.model.more.MoreProduct
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CommunityPostAdapter(val onPostSelected:(postId:String)->Unit) : ListAdapter<Post,CommunityPostAdapter.ViewHolder>
    (object : DiffUtil.ItemCallback<Post>()
{
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean
    {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean
    {
        return oldItem.toString() == newItem.toString()
    }
})
{


    inner class ViewHolder(private val binding: LayoutCommunityPostItemBinding) : RecyclerView.ViewHolder(binding.root)
    {

        fun bind(post: Post?)
        {

            binding.apply()
            {

                layoutCommunityPostItemDescription.text = post?.description
                layoutCommunityPostItemTitle.text = post?.title
                layoutCommunitySectionPostImage.load(post?.image)
                binding.layoutCommunityPostItemDate.text = post?.createdAt?.substring(0,10)

            } // apply closed

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view =
            LayoutCommunityPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener()
        {
            getItem(position).id?.let { it1 -> onPostSelected(it1) }
        }

    } // onBindViewHolder closed


}

