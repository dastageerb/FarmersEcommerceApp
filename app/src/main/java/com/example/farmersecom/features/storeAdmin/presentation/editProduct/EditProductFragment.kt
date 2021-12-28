package com.example.farmersecom.features.storeAdmin.presentation.editProduct

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toFile
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentEditProductBinding
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.features.storeAdmin.domain.model.categories.Category
import com.example.farmersecom.features.storeAdmin.domain.model.editProduct.EditProduct
import com.example.farmersecom.features.storeAdmin.presentation.addNewProduct.CategoriesAdapter
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.android.material.button.MaterialButton
import com.squareup.moshi.Json
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber


@AndroidEntryPoint
class EditProductFragment : BaseFragment<FragmentEditProductBinding>()
{

    lateinit var categoryId:String

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


        binding.autoCompleteEditProductProductCategory.inputType = InputType.TYPE_NULL
        editProductViewModel.getAllCategories()

        editProductViewModel.getProductId.asLiveData().observe(viewLifecycleOwner)
        {
            editProductViewModel.categoriesResponse
            editProductViewModel.getProductDetails(it)
        } //

        subscribeProductDetailsResponseFlow()
        subscribeToGetALLCategoriesFlow()

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

            //   val productQuantity = autoCompleteEditProductProductQuantity.text.toString().trim()
            val productCategory = autoCompleteEditProductProductCategory.text.toString().trim()


            val productPriceInRupees = editTextEditProductPriceInRupees.text.toString().trim()

            if( validateData(productName,productDescription, quantityUnit,
                    productCategory,productPriceInRupees)
                )
            {

                val product = EditProduct(categoryId
                    ,productDescription
                    ,productName
                    ,productPriceInRupees.toInt()
                    ,quantityUnit)
                editProductViewModel.editProduct(product)
            } // if closed
        } // apply closed

    } // updateProduct closed

    private fun validateData(name: String,description: String,unit: String,category: String,
                             productPrice: String) :Boolean
    {


        if (!this::categoryId.isInitialized)
        {
            requireContext().showToast(getString(R.string.select_category_toast))
        }
        return name.nonEmpty { binding.editTextEditProductName.error = it }
                && description.nonEmpty { binding.editTextEditProductDescription.error = it}
                && unit.nonEmpty { requireContext().showToast(getString(R.string.select_product_unit_toast)) }
                && category.nonEmpty() { requireContext().showToast(getString(R.string.select_category_toast)) }
                && productPrice.nonEmpty() { binding.editTextEditProductPriceInRupees.error = it }

    } // validate Data closed



    private fun subscribeToProductUpdatedResponse()
    {

        // when product updated
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
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
                            requireContext().showToast("Loading Categories")
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Success ->
                        {
                            val categoriesAdapter =
                                CategoriesAdapter(
                                    {
                                       binding.autoCompleteEditProductProductCategory.setText(it.name)
                                        categoryId = it.id.toString()
                                    },requireContext(),
                                    R.layout.layout_categories_item,
                                    it.data?.categoryList as MutableList<Category>)
                            binding.autoCompleteEditProductProductCategory.setAdapter(categoriesAdapter)
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

    } // subscribeProfileResponseFlow closed


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
    } // subscribeProfileResponseFlow closed


    private fun updateViews(data: ProductDetailsResponse?)
    {
        binding.editTextEditProductName.setText(data?.productName)
        binding.editTextEditProductPriceInRupees.setText( data?.productPrice.toString())
        binding.editTextEditProductDescription.setText( data?.productDescription)
        binding.autoCompleteEditProductProductCategory.setText(data?.productCategory?.name)

        if(data?.productUnit.equals("Kilo",true))
        {
            binding.buttonProductQuantityUnitGroup.check(R.id.buttonProductQuantityUnitKilo)
        }else if(data?.productUnit.equals("Dozen",true))
        {
            binding.buttonProductQuantityUnitGroup.check(R.id.buttonProductQuantityUnitDozen)
        }else
        {
            binding.buttonProductQuantityUnitGroup.check(R.id.buttonProductQuantityUnitLiter)
        }
        
        categoryId = data?.productCategory?.id!!
        binding.fragmentEditProductLayout.show()


    } // updateView closed


} // EditProductFragment closed