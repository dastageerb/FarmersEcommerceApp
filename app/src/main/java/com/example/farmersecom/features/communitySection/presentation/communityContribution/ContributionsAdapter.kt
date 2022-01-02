package com.example.farmersecom.features.communitySection.presentation.communityContribution

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.databinding.LayoutCommunityContributionsItemBinding
import com.example.farmersecom.features.communitySection.domain.models.Post
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load

class ContributionsAdapter(val onPostSelected:(postId:String)->Unit) : ListAdapter<Post,ContributionsAdapter.ViewHolder>
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


    inner class ViewHolder(private val binding: LayoutCommunityContributionsItemBinding) : RecyclerView.ViewHolder(binding.root)
    {

        fun bind(post: Post?)
        {

            binding.apply()
            {

                layoutCommunityContributionItemDescriptionTextView.text = post?.description
                layoutCommunityContributionItemTitleTextView.text = post?.title
                layoutCommunityContributionImage.load(post?.image)
            } // apply closed

            binding.layoutCommunityContributionDeleteImageView.setOnClickListener()
            {
                post?.id?.let { it1 -> onPostDeleteListener?.let { it2 -> it2(it1) } }
            }

            binding.layoutCommunityContributionEditImageView.setOnClickListener()
            {
                post?.id?.let { it1 -> onPostEditListener?.let { it2 -> it2(it1) } }
            }



        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view =
            LayoutCommunityContributionsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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



    private var onPostDeleteListener:((postId:String)->Unit)? =null
    fun onPostDeleteClicked(listener:(postId:String)->Unit)
    {
        onPostDeleteListener = listener
    }



    private var onPostEditListener:((postId:String)->Unit)? =null
    fun onEditPostClicked(listener:(postId:String)->Unit)
    {
        onPostEditListener= listener
    }



}

