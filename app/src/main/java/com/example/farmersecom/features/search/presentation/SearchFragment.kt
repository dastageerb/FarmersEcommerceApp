package com.example.farmersecom.features.search.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentSearchBinding
import com.example.farmersecom.features.search.presentation.adapter.SearchItemAdapter
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
class SearchFragment : BaseFragment<FragmentSearchBinding>()
{

    val viewModel:SearchViewModel by viewModels()
    lateinit var  searchItemAdapter: SearchItemAdapter
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentSearchBinding
    {
        return FragmentSearchBinding.inflate(inflater,container,false);
    } // onCreateView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        initView()

    } // onViewCreated closed


    private fun initView()
    {

        setupRecycler(binding.recyclerViewSearchFragment)
        binding.fragmentSearchFiltersImageView.setOnClickListener()
        {
            findNavController().navigate(R.id.action_searchFragment_to_filtersFragment)
        } // filtersImageView closed


        binding.fragmentSearchSearchImageView.setOnClickListener()
        {
            val searchQuery = binding.editTextSearchFragmentSearch.text.toString().trim()
            if(searchQuery.isEmpty())
            {
                requireContext().showToast(getString(R.string.enter_product_name))
            }else
            {
                doSearchWithQuery(searchQuery)
            } // else closed
        } //  searchClick closed


        checkFilterNotifier()

        subscribeToSearchResponseFlow()
    }

    private fun checkFilterNotifier()
    {
        if(!viewModel.getLocation().isNullOrEmpty() || !viewModel.getCategory().isNullOrEmpty() )
        {
            binding.fragmentSearchFilterNotifierCarView.show()
        }else
        {
            binding.fragmentSearchFilterNotifierCarView.hide()
        }
    }


    private fun subscribeToSearchResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.searchResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            binding.fragmentSearchProgressbar.hide()
                            Timber.tag(Constants.TAG).d("${it.data}")
                            searchItemAdapter.submitList(it.data?.products)

                            // updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")

                            binding.fragmentSearchProgressbar.hide()
                        }
                        is NetworkResource.Loading ->
                        {
                            binding.fragmentSearchProgressbar.show()
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeToSearchResponseFlow


    private fun setupRecycler(recycler: RecyclerView)
    {
        searchItemAdapter = SearchItemAdapter();
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = searchItemAdapter
    } // setupHomeSlider closed

    private fun doSearchWithQuery(query: CharSequence?)
    {
        query?.let()
        {
            if (query.isEmpty())
            {
                requireContext().showToast(" Please Enter Some Text ")
                return@let
            } // if closed


            // viewModel.searchProduct(query.toString(),viewModel.getCategory(),viewModel.getLocation())



            if(viewModel.getCategory().isNullOrEmpty() && viewModel.getLocation().isNullOrEmpty())
            {
                Timber.tag(TAG).d("1")
                   viewModel.searchProduct(query.toString())
            }else if(!viewModel.getCategory().isNullOrEmpty() && viewModel.getLocation().isNullOrEmpty())
            {

                Timber.tag(TAG).d("2")
                viewModel.searchProduct(query.toString(),category = viewModel.getCategory())
            }else if(viewModel.getCategory().isNullOrEmpty() && !viewModel.getLocation().isNullOrEmpty())
            {

                Timber.tag(TAG).d("3")
                viewModel.searchProduct(query.toString(),location = viewModel.getLocation())
            }
            else
            {

                Timber.tag(TAG).d("4")
                viewModel.searchProduct(query.toString(),viewModel.getCategory(),viewModel.getLocation())
            }
        }
    } // doSearchWithQuery closed


} // searchFragment closed