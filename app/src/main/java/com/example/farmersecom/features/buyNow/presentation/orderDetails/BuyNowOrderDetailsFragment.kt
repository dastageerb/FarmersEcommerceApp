package com.example.farmersecom.features.buyNow.presentation.orderDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentBuyNowDetailsBinding
import com.example.farmersecom.features.authentication.presentation.register.utils.Utils.setUpAdapter
import com.example.farmersecom.features.buyNow.domain.model.request.OrderRequest
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
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

    private var subTotal = 0;
    private var total =0

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


        val paymentOptionList = mutableListOf<String>()
        paymentOptionList.add("COD")
        paymentOptionList.add("Card")
        binding.fragmentBuyNowCustomerPaymentOptionAutoComplete.inputType = InputType.TYPE_NULL
        binding.fragmentBuyNowCustomerPaymentOptionAutoComplete.setUpAdapter(requireContext(),paymentOptionList)


        //binding.fragmentBuyNowCustomerPaymentOptionAutoComplete.setonclick

        orderViewModel?.product?.let { setupProductDetails(it) }



    } // initView closed

    @SuppressLint("SetTextI18n")
    private fun setupProductDetails(data: ProductDetailsResponse)
    {

        binding.imageViewLayoutOrderItemsProductImage.load(data.productPictures!![0].img)
        binding.textViewLayoutOrderItemsProductName.text = data.productName
        binding.textViewLayoutOrderItemsItemProductPrice.text = data.productPrice.toString()
        binding.layoutOrderItemsQuantityTextView.text =data.productQuantity.toString()

        subTotal = data.productQuantity?.let { data.productPrice?.times(it) }!!
        total = subTotal+ data.productDeliveryCharges!!

        binding.fragmentBuyNowDeliveryChargesTextView.text = data.productDeliveryCharges.toString()
        binding.fragmentBuyNowSubTotalTextView.text = subTotal.toString()
        binding.fragmentBuyNowTotalPriceTextView.text = total.toString()

        binding.layoutOrderItemIncreaseQuantityImageView.setOnClickListener()
        {
            onQuantityIncrease()
        }

        binding.layoutOrderItemDecreaseQuantityImageView.setOnClickListener()
        {
            onQuantityDecrease()
        }

        binding.fragmentBuyNowPlaceOrderButton.setOnClickListener(this)

    } // setupProductDetailsResponse closed

    private fun onQuantityDecrease()
    {
        var quantity = binding.layoutOrderItemsQuantityTextView.text.toString().toInt()
        if(quantity>1)
        {
            quantity--
            binding.layoutOrderItemsQuantityTextView.text = quantity.toString()
            subTotal = orderViewModel.product?.productPrice?.times(quantity)!!
            total = subTotal+ orderViewModel.product!!.productDeliveryCharges!!
            binding.fragmentBuyNowSubTotalTextView.text = subTotal.toString()
            binding.fragmentBuyNowTotalPriceTextView.text = total.toString()
        }
    }

    private fun onQuantityIncrease()
    {
        var quantity = binding.layoutOrderItemsQuantityTextView.text.toString().toInt()
        if(quantity<10)
        {
            quantity++
            binding.layoutOrderItemsQuantityTextView.text = quantity.toString()
            subTotal = orderViewModel.product?.productPrice?.times(quantity)!!
            total = subTotal+ orderViewModel.product!!.productDeliveryCharges!!
            binding.fragmentBuyNowSubTotalTextView.text = subTotal.toString()
            binding.fragmentBuyNowTotalPriceTextView.text = total.toString()
        }

    }

    private fun confirmOrder()
    {
        val customerName = binding.fragmentBuyNowCustomerNameEditText.text.toString()
        val city = binding.fragmentBuyNowCustomerCityAutoComplete.text.toString()
        val postalCode= binding.fragmentBuyNowCustomerPostalCodeEditText.text.toString()
        val address = binding.fragmentBuyNowCustomerAddressEditText.text.toString()
        val number =binding.fragmentBuyNowCustomerContactEditText.text.toString();
        val paymentOption = binding.fragmentBuyNowCustomerPaymentOptionAutoComplete.text.toString()

//        if(!this::paymentOption.isInitialized)
//        {
//
//            return
//        }

        if(validateFieldNotEmpty(customerName,city,postalCode,address,number,paymentOption))
        {
            val quantity = binding.layoutOrderItemsQuantityTextView.text.toString().toInt()
            val orderRequest = OrderRequest(address,city,customerName,paymentOption,postalCode.toInt(),quantity,number.toString())
            orderViewModel.product?.productId?.let { orderViewModel.placeOrder(it,orderRequest) }
        }


    } // Confirm order

    private fun validateFieldNotEmpty(customerName: String, city: String, postalCode: String, address: String, contact: String, paymentOption: String): Boolean
    {


        return customerName.nonEmpty { binding.fragmentBuyNowCustomerNameEditText.error = it }
                &&  contact.validator()
            .nonEmpty()
            .minLength(11)
            .maxLength(11)
            .addErrorCallback { binding.fragmentBuyNowCustomerContactEditText.error = it }
            .check()
                && city.nonEmpty { binding.fragmentBuyNowCustomerAddressEditText.error = it }
                && address.nonEmpty { binding.fragmentBuyNowCustomerAddressEditText.error = it }
                && postalCode.validator()
            .nonEmpty()
            .minLength(5)
            .maxLength(5)
            .addErrorCallback { binding.fragmentBuyNowCustomerPostalCodeEditText.error = it }
            .check()
                && paymentOption.nonEmpty {   requireContext().showToast("Choose payment Option") }



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