package com.example.farmersecom.features.cart.presentation.cartCheckOut

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentCartCheckOutBinding
import com.example.farmersecom.features.authentication.presentation.register.utils.Utils.setUpAdapter
import com.example.farmersecom.features.buyNow.domain.model.request.OrderRequest
import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.features.cart.domain.CheckOutItem
import com.example.farmersecom.features.cart.presentation.CartViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class CartCheckOutFragment : BaseFragment<FragmentCartCheckOutBinding>()
{
    
    
    val cartViewModel:CartViewModel by viewModels() 
    val list = mutableListOf<CartItem>()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean):FragmentCartCheckOutBinding
    {
        return FragmentCartCheckOutBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initViews()

        binding.fragmentCartCheckOutPlaceOrderButton.setOnClickListener()
        {
            checkOutCart()
        }

    } // onViewCreated closed

    private fun initViews()
    {
        binding.fragmentCartCheckOutCustomerCityAutoComplete.inputType = InputType.TYPE_NULL
        binding.fragmentCartCheckOutCustomerCityAutoComplete.setUpAdapter(requireContext(),R.array.Sindh)


        val paymentOptionList = mutableListOf<String>()
        paymentOptionList.add("Cash On Delivery")
        binding.fragmentCartCheckOutCustomerPaymentOptionAutoComplete.inputType = InputType.TYPE_NULL
        binding.fragmentCartCheckOutCustomerPaymentOptionAutoComplete.setUpAdapter(requireContext(),paymentOptionList)


        cartViewModel.getAllCartItems.asLiveData().observe(viewLifecycleOwner)
        {
            list.addAll(it)
            Timber.tag(Constants.TAG).d("${it}")
        } //


        cartViewModel.getSubtotal.asLiveData().observe(viewLifecycleOwner)
        {
            Timber.tag(Constants.TAG).d("${it}")
            binding.fragmentCartCheckOutSubTotalTextView.text = it.toString()
        } //

        cartViewModel.getTotal.asLiveData().observe(viewLifecycleOwner)
        {
            Timber.tag(Constants.TAG).d("${it}")
            binding.fragmentCartCheckOutTotalPriceTextView.text = it.toString()
        } //

        cartViewModel.getDeliveryCharges.asLiveData().observe(viewLifecycleOwner)
        {
            Timber.tag(Constants.TAG).d("${it}")
            binding.fragmentCartCheckOutDeliveryChargesTextView.text = it.toString()
        } //



    } // initViewClosed


    fun checkOutCart()
    {
        
        val customerName = binding.fragmentCartCheckOutCustomerNameEditText.text.toString()
        val city = binding.fragmentCartCheckOutCustomerCityAutoComplete.text.toString()
        val postalCode= binding.fragmentCartCheckOutCustomerPostalCodeEditText.text.toString()
        val address = binding.fragmentCartCheckOutCustomerAddressEditText.text.toString()
        val number =binding.fragmentCartCheckOutCustomerContactEditText.text.toString();
        val paymentOption = binding.fragmentCartCheckOutCustomerPaymentOptionAutoComplete.text.toString()

        if(validateFieldNotEmpty(customerName,city,postalCode,address,number,paymentOption))
        {
            val checkOutItem =CheckOutItem(address,city,customerName,paymentOption,postalCode.toInt(),number)
            cartViewModel.checkOutCart(list,checkOutItem)
        }


    } // checkOutCart

    private fun validateFieldNotEmpty(customerName: String, city: String, postalCode: String, address: String, contact: String, paymentOption: String): Boolean
    {


        return customerName.nonEmpty { binding.fragmentCartCheckOutCustomerNameEditText.error = it }
            &&  contact.validator()
        .nonEmpty()
        .minLength(11)
        .maxLength(11)
        .addErrorCallback { binding.fragmentCartCheckOutCustomerContactEditText.error = it }
        .check()
            && city.nonEmpty { binding.fragmentCartCheckOutCustomerAddressEditText.error = it }
            && address.nonEmpty { binding.fragmentCartCheckOutCustomerAddressEditText.error = it }
            && postalCode.validator()
        .nonEmpty()
        .minLength(5)
        .maxLength(5)
        .addErrorCallback { binding.fragmentCartCheckOutCustomerPostalCodeEditText.error = it }
        .check()
            && paymentOption.nonEmpty {   requireContext().showToast("Choose payment Option") }

    }


} //