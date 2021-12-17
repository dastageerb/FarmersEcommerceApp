package com.example.farmersecom.features.orderDetails.presentation.orderDetails

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentOrderDetailsBinding
import com.example.farmersecom.features.authentication.presentation.register.utils.Utils.setUpAdapter
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.utils.constants.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>()
{


    private val orderViewModel: PlaceOrderViewModel by activityViewModels()
    var list = emptyList<ProductDetailsResponse>()
    val adapter  = OrderItemsAdapter()
   // private var itemsList:List<String> = emptyList<String>()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentOrderDetailsBinding
    {
        return FragmentOrderDetailsBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initView()

        // getItemsFrom ProductPage or Cart
        orderViewModel
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO)
        {
            orderViewModel.getOrderItems().collect()
            {
                Timber.tag(TAG).d("$it")
                list = it
                adapter.submitList(list)
            }
        }
    } // onViewCreated closed

    private fun initView()
    {
        binding.fragmentOrderDetailsCustomerCityAutoComplete.inputType = InputType.TYPE_NULL
        binding.fragmentOrderDetailsCustomerCityAutoComplete.setUpAdapter(requireContext(),R.array.Sindh)




        binding.fragmentOrderDetailsItemsList.setHasFixedSize(true)
        binding.fragmentOrderDetailsItemsList.layoutManager = LinearLayoutManager(requireContext())
        binding.fragmentOrderDetailsItemsList.adapter = adapter


        //
        binding.fragmentOrderDetailsPaymentOptionsList.setHasFixedSize(true)
        binding.fragmentOrderDetailsPaymentOptionsList.layoutManager = LinearLayoutManager(requireContext())
        val paymentOptionsAdapter = PaymentOptionsAdapter()
        binding.fragmentOrderDetailsPaymentOptionsList.adapter = paymentOptionsAdapter
        //paymentOptionsAdapter.submitList(list)

    } // initView closed

} // OrderDetailsFragment closed