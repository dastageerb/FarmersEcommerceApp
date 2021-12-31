package com.example.farmersecom.features.storeAdmin.presentation.editProduct

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentEditProductBinding
import com.example.farmersecom.features.authentication.presentation.register.utils.Utils.setUpAdapter
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.features.search.domain.model.categories.CategoriesResponse
import com.example.farmersecom.features.search.domain.model.categories.Category
import com.example.farmersecom.features.storeAdmin.domain.model.editProduct.EditProduct
import com.example.farmersecom.features.storeAdmin.presentation.addNewProduct.CategoriesAdapter
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.android.material.button.MaterialButton
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber


@AndroidEntryPoint
class EditProductFragment : BaseFragment<FragmentEditProductBinding>()
{

    lateinit var categoryId:String
    lateinit var productId:String
    lateinit var job: Job
    private val editProductViewModel:EditProductViewModel by activityViewModels()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentEditProductBinding
    {
        return FragmentEditProductBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        initView()
        
 
        
        
    } // onViewCreated closed

    private fun initView()
    {



        binding.autoCompleteEditProductProductCity.inputType = InputType.TYPE_NULL
        binding.autoCompleteEditProductProductCity.setUpAdapter(requireContext(),R.array.Sindh)

        binding.autoCompleteEditProductProductCategory.inputType = InputType.TYPE_NULL
        editProductViewModel.getAllCategories()

        editProductViewModel.getProductId.asLiveData().observe(viewLifecycleOwner)
        {
            productId = it
            editProductViewModel.categoriesResponse
            editProductViewModel.getProductDetails(it)
        } //

        subscribeProductDetailsResponseFlow()
        subscribeToGetALLCategoriesFlow()
        subscribeToProductUpdatedResponse()

        binding.fragmentEditProductUpdateButton.setOnClickListener()
        {
            updateProduct()
        }
    } // initView closed

    private fun updateProduct()
    {

        binding.apply()
        {

            val productName = editTextEditProductName.text.toString().trim();
            val productDescription = editTextEditProductDescription.text.toString().trim();
            /// Get Selected Quantity Button Id first
            val quantityButtonId   = buttonProductQuantityUnitGroup.checkedButtonId
            // then get Quantity based on selected button
            val quantityUnit = buttonProductQuantityUnitGroup
                .findViewById<MaterialButton>(quantityButtonId).text.toString()

            val productCity = autoCompleteEditProductProductCity.text.toString().trim()

            val productCategory = autoCompleteEditProductProductCategory.text.toString().trim()


            val productPriceInRupees = editTextEditProductPriceInRupees.text.toString().trim()

            if( validateData(productName,productDescription, quantityUnit,
                    productCategory,productPriceInRupees,productCity)
                )
            {

                val product = EditProduct(categoryId
                    ,productDescription
                    ,productName
                    ,productPriceInRupees.toInt()
                    ,quantityUnit,productCity)
                editProductViewModel.editProduct(product,productId)
            } // if closed
        } // apply closed

    } // updateProduct closed

    private fun validateData(name: String,description: String,unit: String,category: String,
                             productPrice: String,city:String) :Boolean
    {


        if (!this::categoryId.isInitialized)
        {
            requireContext().showToast(getString(R.string.select_category_toast))
        }
        return name.nonEmpty { binding.editTextEditProductName.error = it }
                && description.nonEmpty { binding.editTextEditProductDescription.error = it}
                && unit.nonEmpty { requireContext().showToast(getString(R.string.select_product_unit_toast)) }
                && city.nonEmpty() {requireContext().showToast(getString(R.string.please_select_city))}
                && category.nonEmpty() { requireContext().showToast(getString(R.string.select_category_toast)) }
                && productPrice.nonEmpty() { binding.editTextEditProductPriceInRupees.error = it }

    } // validate Data closed



    private fun subscribeToProductUpdatedResponse()
    {

        // when product updated
        job = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                editProductViewModel.statusMsgResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            binding.editProductFragmentProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.data}")
                            requireContext().showToast(it.data?.message.toString())
                            findNavController().navigate(R.id.action_editProductFragment_to_discontinuedProductsFragment)


                        }
                        is NetworkResource.Error ->
                        {
                            binding.editProductFragmentProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                        is NetworkResource.Loading -> binding.editProductFragmentProgressBar.show()
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeToActiveProductsResponse



    private fun subscribeToGetALLCategoriesFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                editProductViewModel.categoriesResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.editProductFragmentProgressBar.show()
                            requireContext().showToast("Loading Categories")
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Success ->
                        {
                           setupCategoriesAdapter(it.data)
                            Timber.tag(Constants.TAG).d("Fragment  ${it.data}")
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d(" Fragment  ${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed

    } // subscribeResponseFlow closed


    private fun setupCategoriesAdapter(it: CategoriesResponse?)
    {

        binding.editProductFragmentProgressBar.hide()
        val categoriesAdapter =
            CategoriesAdapter(
                {
                    // onClick
                    binding.autoCompleteEditProductProductCategory.setText(it.name)

                    if(it.name.equals("cattle",true))
                    {
                        binding.buttonProductQuantityUnitGroup.check(R.id.buttonProductQuantityUnitCattle)
                    }

                    categoryId = it.id.toString()
                },requireContext(),
                R.layout.layout_categories_item,
                it?.categoryList as MutableList<Category>)
        binding.autoCompleteEditProductProductCategory.setAdapter(categoriesAdapter)

    } // setupCategoriesAdapter closed


    private fun subscribeProductDetailsResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                editProductViewModel.productDetailsResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            binding.editProductFragmentProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.data}")
                            updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            binding.editProductFragmentProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                        is NetworkResource.Loading ->
                        {
                            binding.editProductFragmentProgressBar.show()
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeResponseFlow closed



    private fun updateViews(data: ProductDetailsResponse?)
    {
        binding.editTextEditProductName.setText(data?.productName)
        binding.editTextEditProductPriceInRupees.setText( data?.productPrice.toString())
        binding.editTextEditProductDescription.setText( data?.productDescription)
        binding.autoCompleteEditProductProductCategory.setText(data?.productCategory?.name)

        binding.autoCompleteEditProductProductCity.setText(data?.productLocation)

        when
        {
            data?.productUnit.equals("Cattle",true) ->
            {
                binding.buttonProductQuantityUnitGroup.check(R.id.buttonProductQuantityUnitCattle)
            }
            data?.productUnit.equals("Liter") ->
            {
                binding.buttonProductQuantityUnitGroup.check(R.id.buttonProductQuantityUnitLiter)
            }
            data?.productUnit.equals("Dozen",true) ->
            {
                binding.buttonProductQuantityUnitGroup.check(R.id.buttonProductQuantityUnitDozen)
            }
            else ->
            {
                binding.buttonProductQuantityUnitGroup.check(R.id.buttonProductQuantityUnitKilo)
            }
        }
        
        categoryId = data?.productCategory?.id!!
        binding.fragmentEditProductLayout.show()


    } // updateView closed

//    override fun onDestroyView()
//    {
//        super.onDestroyView()
//        job.cancel()
//    }

    override fun onStop()
    {
        super.onStop()
        job.cancel()

    }

} // EditProductFragment closed