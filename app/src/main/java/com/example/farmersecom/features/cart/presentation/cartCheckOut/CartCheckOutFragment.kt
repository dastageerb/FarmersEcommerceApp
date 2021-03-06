package com.example.farmersecom.features.cart.presentation.cartCheckOut

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
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

        binding.fragmentCartCheckOutConfirmButton.setOnClickListener()
        {
            checkOutCart()
        }
        subscribeSliderResponse()

    } // onViewCreated closed


    private fun subscribeSliderResponse()
    {
        viewLifecycleOwner.lifecycleScope.launch()
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                cartViewModel.placeOrderResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.fragmentCartCheckOutProgressBar.show()
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Error ->
                        {

                            Timber.tag(Constants.TAG).d(it.msg)
                            binding.fragmentCartCheckOutProgressBar.hide()
                        }
                        is NetworkResource.Success ->
                        {
                            binding.fragmentCartCheckOutProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.data}")
                            findNavController().navigate(R.id.action_cartCheckOutFragment_to_currentOrdersFragment)
                        }
                        else -> {}
                    } /// when closed
                } // collect closed
            } // repeatOnLifeCycle closed
        } // lifeCycleScope closed
    } // subscribeRegisterResponse  closed

    private fun initViews()
    {
        binding.fragmentCartCheckOutCustomerCityAutoComplete.inputType = InputType.TYPE_NULL
        binding.fragmentCartCheckOutCustomerCityAutoComplete.setUpAdapter(requireContext(),R.array.Sindh)


        val paymentOptionList = mutableListOf<String>()
        paymentOptionList.add(getString(R.string.cod))
        binding.fragmentCartCheckOutCustomerPaymentOptionAutoComplete.inputType = InputType.TYPE_NULL
        binding.fragmentCartCheckOutCustomerPaymentOptionAutoComplete.setUpAdapter(requireContext(),paymentOptionList)


        cartViewModel.getUser()?.let()
        {
            binding.fragmentCartCheckOutCustomerNameEditText.setText(it.fullName)
            binding.fragmentCartCheckOutCustomerContactEditText.setText(it.contactNumber)
            binding.fragmentCartCheckOutCustomerPostalCodeEditText.setText(it.postalCode.toString())
            binding.fragmentCartCheckOutCustomerAddressEditText.setText(it.address)
            binding.fragmentCartCheckOutCustomerCityAutoComplete.setText(it.city)
            binding.fragmentCartCheckOutCustomerCityAutoComplete.setUpAdapter(requireContext(),R.array.Sindh)

        }
        
        
        
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


    private fun checkOutCart()
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
            && paymentOption.nonEmpty {   requireContext().showToast(getString(R.string.choose_payment_option)) }

    } // validateFieldsNotEmpty closed


} //