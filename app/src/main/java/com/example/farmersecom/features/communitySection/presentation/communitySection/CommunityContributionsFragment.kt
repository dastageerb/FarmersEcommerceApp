package com.example.farmersecom.features.communitySection.presentation.communitySection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentCommunityContributionsBinding
import com.example.farmersecom.features.communitySection.presentation.CommunitySectionViewModel
import com.example.farmersecom.features.communitySection.presentation.community.CommunityPostAdapter
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class CommunityContributionsFragment : BaseFragment<FragmentCommunityContributionsBinding>()
{

    private val viewModel: CommunitySectionViewModel by activityViewModels()
    lateinit var communityContributionsAdapter: ContributionsAdapter
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentCommunityContributionsBinding
    {
        return FragmentCommunityContributionsBinding.inflate(inflater,container,false)
    } // createView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerVie(binding.fragmentCommunityContributionsRecyclerView)
        viewModel.getAllCommunityPosts()
        subscribeToCommunityPostsResponse()
    } // onViewCreated

    private fun subscribeToCommunityPostsResponse()
    {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.getCommunityPostsResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.fragmentCommunityContributionsProgressBar.show()
                        }
                        is NetworkResource.Error ->
                        {
                            binding.fragmentCommunityContributionsProgressBar.hide()
                        }
                        is NetworkResource.Success ->
                        {
                            binding.fragmentCommunityContributionsProgressBar.hide()
                           communityContributionsAdapter.submitList(it.data?.posts)
                            Timber.tag(Constants.TAG).d(it.data?.posts.toString())
                        }
                    } // when closed
                } // collector closed
            } // viewLifeCycleOwner.repeatOnLifecycle closed
        } // coroutineScope closed

    } // subscribeToCommunityPostsResponse

    private fun setupRecyclerVie(recyclerView: RecyclerView)
    {
        communityContributionsAdapter = ContributionsAdapter ()
        {

        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = communityContributionsAdapter
    }











} // CommunitySectionFragment closed