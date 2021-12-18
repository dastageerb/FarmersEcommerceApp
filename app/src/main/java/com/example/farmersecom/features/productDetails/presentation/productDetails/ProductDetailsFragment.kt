package com.example.farmersecom.features.productDetails.presentation.productDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentProductDetailsBinding
import com.example.farmersecom.features.orderDetails.presentation.orderDetails.PlaceOrderViewModel
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.features.productDetails.domain.model.ProductPicture
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>()
{
    val id = "61b3062c658dc50016921ad0"
    private val orderViewModel: PlaceOrderViewModel by activityViewModels()
    private val viewModel: ProductDetailsViewModel by activityViewModels()
    val list = mutableListOf<ProductDetailsResponse>()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentProductDetailsBinding
    {
        return FragmentProductDetailsBinding.inflate(inflater,container,false);
    } // onCreateView closed




    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewModel.getProductId.collect()
            {
                viewModel.getProductDetails(it)
            } //
        } // viewLifeCycleOwner



        subscribeProductDetailsResponseFlow()
        binding.buttonProductDetailsFragmentBuyNow.setOnClickListener()
        {

            orderViewModel.setList(list)
            findNavController().navigate(R.id.action_productDetailsFragment_to_orderDetailsFragment)

        }

    } // onViewCreated



    private fun subscribeProductDetailsResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.productDetailsResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d("${it.data}")
                            updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeProfileResponseFlow closed


    @SuppressLint("SetTextI18n")
    private fun updateViews(data: ProductDetailsResponse?)
    {
        data?.let { list.add(it) }
        data?.let { setupRecyclerView(binding.recyclerViewFragmentProductDetails, it.productPictures) }
        binding.textViewFragmentProductDetailsProductName.text = data?.productName
        binding.textViewFragmentProductDetailsProductPrice.text = "RS."+data?.productPrice.toString()
        binding.textViewProductQuantityFragmentProductDetails.text = data?.productQuantity.toString()
        binding.textViewProductQuantityUnitFragmentProductDetails.text = data?.productUnit
        binding.textViewProductDescription.text = data?.productDescription


    } // updateView closed


    private fun setupRecyclerView(recycler: RecyclerView,list: List<ProductPicture>)
    {
        val adapter = ProductImageAdapter();
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL ,false)
        recycler.adapter = adapter
        var snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler);
        adapter.submitList(list)


    } // setupRecyclerView

} // ProductDetailsFragment