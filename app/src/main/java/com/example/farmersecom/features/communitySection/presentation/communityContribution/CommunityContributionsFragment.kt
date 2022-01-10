package com.example.farmersecom.features.communitySection.presentation.communityContribution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentCommunityContributionsBinding
import com.example.farmersecom.features.communitySection.presentation.CommunitySectionViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class CommunityContributionsFragment : BaseFragment<FragmentCommunityContributionsBinding>() ,View.OnClickListener
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

        initViews()
    } // onViewCreated


    private fun initViews()
    {
        binding.fragmentCommunityContributionsAddPostButton.setOnClickListener(this)
        setupRecyclerVie(binding.fragmentCommunityContributionsRecyclerView)
        viewModel.getUserContributionsPosts()
        subscribeToCommunityPostsResponse()
        subscribeToStatusResponse()
    }

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
                            Timber.tag(TAG).d(it.msg)
                            binding.fragmentCommunityContributionsProgressBar.hide()
                        }
                        is NetworkResource.Success ->
                        {
                            if(it.data?.posts?.isNullOrEmpty() == true)
                            {
                                requireContext().showToast(getString(R.string.no_contributions_yet))
                            }
                            binding.fragmentCommunityContributionsProgressBar.hide()
                           communityContributionsAdapter.submitList(it.data?.posts!!)
                            Timber.tag(Constants.TAG).d(it.data?.posts.toString())
                        }
                    } // when closed
                } // collector closed
            } // viewLifeCycleOwner.repeatOnLifecycle closed
        } // coroutineScope closed

    } // subscribeToCommunityPostsResponse



    /// delete post response
    private fun subscribeToStatusResponse()
    {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.statusMsgResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.fragmentCommunityContributionsProgressBar.show()
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(TAG).d(""+it.msg)
                            binding.fragmentCommunityContributionsProgressBar.hide()
                        }
                        is NetworkResource.Success ->
                        {
                            it.data?.message?.let { it1 -> requireContext().showToast(it1) }
                            binding.fragmentCommunityContributionsProgressBar.hide()
                           // communityContributionsAdapter.submitList(it.data?.posts)
                            //T//imber.tag(Constants.TAG).d(it.data?.posts.toString())
                        }
                    } // when closed
                } // collector closed
            } // viewLifeCycleOwner.repeatOnLifecycle closed
        } // coroutineScope closed

    } // subscribeToStatusResponse


    private fun setupRecyclerVie(recyclerView: RecyclerView)
    {
        communityContributionsAdapter = ContributionsAdapter ()
        {
            viewModel.setPostId(it)
            findNavController().navigate(R.id.action_communityContributionsFragment_to_postDetailsFragment)
        }


        communityContributionsAdapter.onPostDeleteClicked()
        {
            viewModel.deletePost(it)
        }
        communityContributionsAdapter.onEditPostClicked()
        {
            viewModel.setPostId(it)
            findNavController().navigate(R.id.action_communityContributionsFragment_to_editPostFragment)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = communityContributionsAdapter
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.fragmentCommunityContributionsAddPostButton ->
            {
                findNavController().navigate(R.id.action_communityContributionsFragment_to_addPostFragment)
            }
        }
    } /// onClick closed


} // CommunitySectionFragment closed