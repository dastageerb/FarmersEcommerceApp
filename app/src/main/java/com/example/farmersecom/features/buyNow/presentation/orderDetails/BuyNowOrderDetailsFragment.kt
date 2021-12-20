package com.example.farmersecom.features.buyNow.presentation.orderDetails

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentBuyNowDetailsBinding
import com.example.farmersecom.features.authentication.presentation.register.utils.Utils.setUpAdapter
import com.example.farmersecom.features.buyNow.domain.model.request.OrderRequest
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class BuyNowOrderDetailsFragment : BaseFragment<FragmentBuyNowDetailsBinding>(), View.OnClickListener
{


    private val orderViewModel: PlaceOrderViewModel by activityViewModels()
    private lateinit var paymentOption:String

   // private var itemsList:List<String> = emptyList<String>()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentBuyNowDetailsBinding
    {
        return FragmentBuyNowDetailsBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initView()

        // getItemsFrom ProductPage or Cart

        subscribePlaceOrderResponse()

    } // onViewCreated closed

    private fun initView()
    {
        binding.fragmentBuyNowCustomerCityAutoComplete.inputType = InputType.TYPE_NULL
        binding.fragmentBuyNowCustomerCityAutoComplete.setUpAdapter(requireContext(),R.array.Sindh)

        orderViewModel?.product?.let { setupProductDetails(it) }


        val paymentOptionList = mutableListOf<String>()
        paymentOptionList.add("COD")
        paymentOptionList.add("Card")

        binding.fragmentBuyNowPaymentOptionsList.setHasFixedSize(true)
        binding.fragmentBuyNowPaymentOptionsList.layoutManager = LinearLayoutManager(requireContext())
        val paymentOptionsAdapter = PaymentOptionsAdapter()
        {
            paymentOption=it
        }

        binding.fragmentBuyNowPaymentOptionsList.adapter = paymentOptionsAdapter
        paymentOptionsAdapter.submitList(paymentOptionList)
    } // initView closed

    private fun setupProductDetails(data: ProductDetailsResponse)
    {

        binding.imageViewLayoutOrderItemsProductImage.load(data.productPictures!![0].img)
        binding.textViewLayoutOrderItemsProductName.text = data.productName
        binding.textViewLayoutOrderItemsItemProductPrice.text = data.productPrice.toString()

        binding.layoutOrderItemsQuantityTextView.text =data.productQuantity.toString()

        binding.layoutOrderItemIncreaseQuantityImageView.setOnClickListener()
        {

        }

        binding.layoutOrderItemDecreaseQuantityImageView.setOnClickListener()
        {

        }

        binding.fragmentBuyNowPlaceOrderButton.setOnClickListener(this)

    } // setupProductDetailsResponse closed

    private fun confirmOrder()
    {
        val customerName = binding.fragmentBuyNowCustomerNameEditText.text.toString()
        val city = binding.fragmentBuyNowCustomerCityAutoComplete.text.toString()
        val postalCode= binding.fragmentBuyNowCustomerPostalCodeEditText.text.toString()
        val address = binding.fragmentBuyNowCustomerAddressEditText.text.toString()

        if(!this::paymentOption.isInitialized)
        {
            requireContext().showToast("Choose payment Option")
            return
        }

        if(validateFieldNotEmpty(customerName,city,postalCode,address,paymentOption))
        {
            val quantity = binding.layoutOrderItemsQuantityTextView.text.toString().toInt()
            val orderRequest = OrderRequest(address,city,customerName,paymentOption,postalCode.toInt(),quantity)

            Timber.tag(TAG).d(""
                    +orderRequest.toString())

            orderViewModel.product?.productId?.let { orderViewModel.placeOrder(it,orderRequest) }
        }


    } // Confirm order

    private fun validateFieldNotEmpty(customerName: String, city: String, postalCode: String, address: String, paymentOption: String?): Boolean
    {


        return customerName.nonEmpty { binding.fragmentBuyNowCustomerNameEditText.error = it }
                && city.nonEmpty { binding.fragmentBuyNowCustomerAddressEditText.error = it }
                && address.nonEmpty { binding.fragmentBuyNowCustomerAddressEditText.error = it }
                && postalCode.validator()
            .nonEmpty()
            .minLength(5)
            .maxLength(5)
            .addErrorCallback { binding.fragmentBuyNowCustomerPostalCodeEditText.error = it }
            .check()



    }

    // handle onclicks
    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.fragmentBuyNowPlaceOrderButton ->
            {
                confirmOrder()
            } // R.id.fragmentBuyNowPlaceOrderButton closed
        } // when closed
    } // onclick closed


    private fun subscribePlaceOrderResponse()
    {
        viewLifecycleOwner.lifecycleScope.launch()
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                orderViewModel.placeOrderResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Error ->
                        {
                            requireContext().showToast(it.msg.toString())
                            Timber.tag(Constants.TAG).d(it.msg)
                        }
                        is NetworkResource.Success ->
                        {
                            requireContext().showToast(it.data?.message.toString())
                            Timber.tag(Constants.TAG).d(it.data.toString())
                        }
                        else -> {}
                    } /// when closed
                } // collect closed
            } // repeatOnLifeCycle closed
        } // lifeCycleScope closed
    } // subscribeRegisterResponse  closed



} // OrderDetailsFragment closed1