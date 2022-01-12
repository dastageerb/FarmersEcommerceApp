package com.example.farmersecom.features.communitySection.presentation.postDetails

import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentPostDetailsBinding
import com.example.farmersecom.features.communitySection.domain.models.Post
import com.example.farmersecom.features.communitySection.presentation.CommunitySectionViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


class PostDetailsFragment : BaseFragment<FragmentPostDetailsBinding>()
{


    private val viewModel:CommunitySectionViewModel by activityViewModels()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentPostDetailsBinding
    {
        return  FragmentPostDetailsBinding.inflate(inflater,container,false)
    } // createView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initViews()


    } // onViewCreated


    private fun initViews()
    {

        viewModel.getPostId.observe(viewLifecycleOwner)
        {
            viewModel.getPostById(it)
            Timber.tag(TAG).d(""+it)
        } // observer closed

        subscribeToPostByIdResponse()

    } // initView closed



    private fun subscribeToPostByIdResponse()
    {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.getPostByIdResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.fragmentPostDetailsProgressBar.show()
                            binding.fragmentPostDetailsLayout.hide()
                        }
                        is NetworkResource.Error ->
                        {

                            Timber.tag(TAG).d(""+it.msg)
                            binding.fragmentPostDetailsProgressBar.hide()

                        }
                        is NetworkResource.Success ->
                        {
                            Timber.tag(TAG).d(""+it.data)
                            updateViews(it.data)
                        }
                    } // when closed
                } // collector closed
            } // viewLifeCycleOwner.repeatOnLifecycle closed
        } // coroutineScope closed

    } // subscribeToCommunityPostDetailsResponse





    private fun updateViews(post: Post?)
    {

        Picasso.get().load(post?.image)
            .into(binding.fragmentPostDetailsImageView, object : Callback
            {
                override fun onSuccess()
                {
                    Timber.tag(TAG).d("Success")
                    try
                    {
                        binding.fragmentPostDetailsProgressBar.hide()
                    }catch (e:Exception)
                    {

                    }

                }
                override fun onError(e: Exception?)
                {

                    Timber.tag(TAG).d("Success")
                    try
                    {
                        binding.fragmentPostDetailsProgressBar.hide()
                    }catch (e:Exception)
                    {

                    }
                }
            })
       // binding.fragmentPostDetailsImageView.load(post?.image)
        binding.fragmentPostDetailsTitleTextView.text = post?.title
        binding.fragmentPostDetailsDescription.text = post?.description
        binding.fragmentPostDetailsCreatedDate.text = post?.createdAt?.substring(0,10)
        binding.fragmentPostDetailsPublisherName.text=post?.createdBy?.firstName
        ///binding.fragmentPostDetailsProgressBar.hide()
        binding.fragmentPostDetailsLayout.show()
    } // updateView closed


    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

} // PostDetailsFragment closed