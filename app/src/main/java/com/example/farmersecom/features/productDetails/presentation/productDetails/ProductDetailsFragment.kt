package com.example.farmersecom.features.productDetails.presentation.productDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentProductDetailsBinding
import com.example.farmersecom.features.buyNow.presentation.orderDetails.PlaceOrderViewModel
import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.features.cart.presentation.CartViewModel
import com.example.farmersecom.features.productDetails.domain.model.NavigationEntity
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.features.productDetails.domain.model.ProductPicture
import com.example.farmersecom.features.productStore.presentation.ProductStoreViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() , View.OnClickListener
{

    private val storeViewModel: ProductStoreViewModel by activityViewModels()
    private val orderViewModel: PlaceOrderViewModel by activityViewModels()
    private val viewModel: ProductDetailsViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by viewModels()
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


        binding.buttonProductDetailsFragmentAddToCart.setOnClickListener(this)
        subscribeProductDetailsResponseFlow()
        binding.buttonProductDetailsFragmentBuyNow.setOnClickListener(this)

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
        binding.ratingBarFragmentProductDetails.rating = data?.productRating!!.toFloat()
        binding.imageViewProductDetailsFragmentStoreImage.load(data.store?.storeImage)
        binding.textViewStoreNameFragmentProductDetails.text = data.store?.storeName
        binding.textViewProductDeliveryCharges.text = "Delivery Charges :"+data.productDeliveryCharges.toString()

        binding.layoutStoreDetails.setOnClickListener()
        {
            data.store?.let()
            {
                Timber.tag(TAG).d(""+it)
                storeViewModel.setStoreId(it.id!!)
                findNavController().navigate(R.id.action_productDetailsFragment_to_productStoreFragment)
            }

        }

    } // updateView closed


    private fun setupRecyclerView(recycler: RecyclerView,list: List<ProductPicture>?)
    {
        val adapter = ProductImageAdapter();
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL ,false)
        recycler.adapter = adapter
        recycler.onFlingListener = null;
        var snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler);
        adapter.submitList(list)

    } // setupRecyclerView

    override fun onClick(v: View?)
    {
        if(viewModel.getAuthToken()?.isEmpty() == true)
        {
            findNavController().navigate(R.id.action_productDetailsFragment_to_logInFragment)
        }
        else
        {
            when(v?.id)
            {
                R.id.buttonProductDetailsFragmentAddToCart ->
                {
                    addToCart()
                }
                R.id.buttonProductDetailsFragmentBuyNow ->
                {
                    orderViewModel.product = list[0]
                    findNavController().navigate(R.id.action_productDetailsFragment_to_orderDetailsFragment)
                }
            }

        } // else closed

    } // onClick closed

    private fun addToCart()
    {
        val product = list[0]
        val cartItem = CartItem(
            product.productId!!,
            product.productName!!,
            product.productQuantity!!,
            product.productPrice!!,
            product.productUnit!!,
            product.productPictures?.get(0)?.img!!,product.productDeliveryCharges!!)
        cartViewModel.insertCartItem(cartItem)
    } // addToCartClosed

} // ProductDetailsFragment