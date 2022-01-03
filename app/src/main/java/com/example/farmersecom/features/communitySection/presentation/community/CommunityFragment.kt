package com.example.farmersecom.features.communitySection.presentation.community

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentCommunityBinding
import com.example.farmersecom.features.communitySection.presentation.CommunitySectionViewModel
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
class CommunityFragment : BaseFragment<FragmentCommunityBinding>(),SearchView.OnQueryTextListener
{


    private val viewModel:CommunitySectionViewModel by activityViewModels()
    lateinit var communityPostAdapter: CommunityPostAdapter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentCommunityBinding
    {
        return  FragmentCommunityBinding.inflate(inflater,container,false)
    } // createView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerVie(binding.fragmentCommunityRecyclerView)
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
                            binding.fragmentCommunityProgressBar.show()
                        }
                        is NetworkResource.Error ->
                        {
                            binding.fragmentCommunityProgressBar.hide()
                        }
                        is NetworkResource.Success ->
                        {
                            binding.fragmentCommunityProgressBar.hide()
                            communityPostAdapter.submitList(it.data?.posts)
                            Timber.tag(TAG).d(it.data?.posts.toString())
                        }
                    } // when closed
                } // collector closed
            } // viewLifeCycleOwner.repeatOnLifecycle closed
        } // coroutineScope closed

    } // subscribeToCommunityPostsResponse

    private fun setupRecyclerVie(recyclerView: RecyclerView)
    {
        communityPostAdapter = CommunityPostAdapter ()
        {
                viewModel.setPostId(it)
            findNavController().navigate(R.id.action_communityFragment_to_postDetailsFragment)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = communityPostAdapter
    } // setupRecyclerView closed


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search,menu)

        val menuItem = menu?.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView  as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)


    } // oncreateOptionsMenu

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.menu_search ->
            {

            }
        } // when closed
        return super.onOptionsItemSelected(item)
    } // onOptionsItemSelected

    // Community search

    override fun onQueryTextSubmit(query: String?): Boolean
    {

        if(query!=null)
        {
            viewModel.getSearchCommunityPosts(query)
        }else
        {
            requireContext().showToast(getString(R.string.enter_query_to_search))
        }
        return true
    } // onQueryTextSubmit closed

    override fun onQueryTextChange(newText: String?): Boolean
    {

        return true
    }


} // CommunityFragment closed