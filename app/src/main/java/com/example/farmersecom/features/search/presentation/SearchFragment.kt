package com.example.farmersecom.features.search.presentation


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentSearchBinding
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@InternalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>()
{

    val viewModel:SearchViewModel by viewModels()


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentSearchBinding
    {
        return FragmentSearchBinding.inflate(inflater,container,false);
    } // onCreateView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        binding.fragmentSearchFiltersImageView.setOnClickListener()
        {
            findNavController().navigate(R.id.action_searchFragment_to_filtersFragment)
        } //


        binding.editTextSearchFragmentSearch.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?)
            {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {}
            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int)
            {
                viewLifecycleOwner.lifecycleScope.launch()
                {
                    delay(300)
                    doSearchWithQuery(query)
                } // onTextChanged
            } // onTextChanged
        }) // addTextListener closed


        subscribeToSearchResponseFlow()

    } // onViewCreated closed

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
                            Timber.tag(Constants.TAG).d("${it.data}")
                            // updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed


    } // subscribeToSearchResponseFlow

    @InternalCoroutinesApi
    private fun doSearchWithQuery(query: CharSequence?)
    {
        query?.let()
        {
            if (query.isEmpty())
            {
                requireContext().showToast(" Please Enter Some Text ")
                return@let
            } // if closed
            viewModel.searchProduct(query.toString(),"default","default")
        }
    } // doSearchWithQuery closed


} // searchFragment closed