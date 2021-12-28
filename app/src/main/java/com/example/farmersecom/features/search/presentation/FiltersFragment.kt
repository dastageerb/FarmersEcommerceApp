package com.example.farmersecom.features.search.presentation

import android.os.Bundle
import android.text.InputType
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
import com.example.farmersecom.databinding.FragmentFiltersBinding
import com.example.farmersecom.features.authentication.presentation.register.utils.Utils.setUpAdapter
import com.example.farmersecom.features.search.domain.model.categories.CategoriesResponse
import com.example.farmersecom.features.search.domain.model.categories.Category
import com.example.farmersecom.features.storeAdmin.presentation.addNewProduct.CategoriesAdapter
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


class FiltersFragment : BaseFragment<FragmentFiltersBinding>()
{


    val viewModel:SearchViewModel by viewModels()
    private var categoryId:String?= null
    var location:String? = null

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentFiltersBinding
    {
        return FragmentFiltersBinding.inflate(inflater,container,false);
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        initView()
    } // onViewCreated closed


    private fun initView()
    {

        binding.fragmentFiltersCategoryAutoCompleteView.inputType = InputType.TYPE_NULL
        binding.fragmentFiltersCityAutoCompleteView.inputType = InputType.TYPE_NULL

        binding.fragmentFiltersCityAutoCompleteView.setUpAdapter(requireContext(),R.array.Sindh)



        binding.fragmentFiltersApplyButton.setOnClickListener()
        {
            applyFilters()
        }

        viewModel.getAllCategories()
        subscribeToGetALLCategoriesFlow()

    } // initView closed

    private fun applyFilters()
    {
       // val category = binding.fragmentFiltersCategoryAutoCompleteView.text.toString().trim()
        location = binding.fragmentFiltersCityAutoCompleteView.text.toString().trim()
        viewModel.saveCategory(categoryId!!)
        viewModel.saveLocation(location!!)
        findNavController().navigate(R.id.action_filtersFragment_to_searchFragment)

    } //


    private fun subscribeToGetALLCategoriesFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.categoriesResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            requireContext().showToast("Loading Categories")
                           binding.fragmentFiltersProgressBar.show()
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Success ->
                        {
                            setupCategoriesAdapter(it.data)

                            Timber.tag(Constants.TAG).d("Fragment  ${it.data}")
                        }
                        is NetworkResource.Error ->
                        {
                            binding.fragmentFiltersProgressBar.hide()
                            Timber.tag(Constants.TAG).d(" Fragment  ${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeProfileResponseFlow closed

    private fun setupCategoriesAdapter(it: CategoriesResponse?)
    {

        binding.fragmentFiltersProgressBar.hide()
        val categoriesAdapter =
            CategoriesAdapter(
                {
                    //binding.autoCompleteAddNewProductProductCategory.inputType =
                    binding.fragmentFiltersCategoryAutoCompleteView.setText(it.name)
                    categoryId = it.id.toString()
                },requireContext(),
                R.layout.layout_categories_item,
                it?.categoryList as MutableList<Category>)
        binding.fragmentFiltersCategoryAutoCompleteView.setAdapter(categoriesAdapter)

    }

} // onFilters closed