package com.example.farmersecom.features.buyerSection.presentation.orderDetailsForBuyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentOrderDetailsForBuyerBinding
import com.example.farmersecom.features.buyerSection.domain.model.orderDetails.OrderDetailsResponse
import com.example.farmersecom.features.buyerSection.presentation.BuyerDashboardViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


class OrderDetailsForBuyerFragment : BaseFragment<FragmentOrderDetailsForBuyerBinding>()
{


    private val viewModel: BuyerDashboardViewModel by activityViewModels()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentOrderDetailsForBuyerBinding
    {
        return FragmentOrderDetailsForBuyerBinding.inflate(inflater,container,false)
    } // createView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.getOrderId.collect()
                {
                    getOrderDetails(it)
                } // collect
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // onViewCreated




    private fun getOrderDetails(orderId: String)
    {
        viewModel.orderDetails(orderId)
        subscribeToOrderDetails()
    }  // getOrderDetailsClosed


    private fun subscribeToOrderDetails()
    {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.orderDetailsResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.progressBarFragmentOrderDetailsForBuyer.show()
                        }
                        is NetworkResource.Success ->
                        {
                            binding.progressBarFragmentOrderDetailsForBuyer.hide()
                            binding.fragmentOrderDetailsForBuyerDetailsLayout.show()
                            Timber.tag(Constants.TAG).d("${it.data}")
                            updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            binding.progressBarFragmentOrderDetailsForBuyer.show()
                            binding.fragmentOrderDetailsForBuyerDetailsLayout.hide()
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    }

    private fun updateViews(data: OrderDetailsResponse?)
    {
        binding.apply()
        {
            // store info
            binding.fragmentOrderDetailsForBuyerStoreNameTextView.text = data?.storeName
            // product info
            binding.fragmentOrderDetailsForBuyerProductNameTextView.text = data?.productName
            binding.fragmentOrderDetailsForBuyerProductDefaultQuantityUnitTextView.text ="yet to code"
            binding.fragmentOrderDetailsForBuyerProductDPriceTextView.text = data?.productprice.toString()
            binding.fragmentOrderDetailsForBuyerProductDefaultQuantityUnitTextView.text = data?.unit
            // orderInfo
            binding.fragmentOrderDetailsForBuyerOrderIdTextView.text = data?.id
            binding.fragmentOrderDetailsForBuyerOrderQuantityTextView.text = data?.orderQuantity.toString()
            binding.fragmentOrderDetailsForBuyerOderDateTextView.text = data?.date?.substring(0,10)
            binding.fragmentOrderDetailsForBuyerOrderDeliveryChargesTextView.text = "yet to code"
            binding.fragmentOrderDetailsForBuyerOrderSubtotalTextView.text = data?.subTotal.toString()
            binding.fragmentOrderDetailsForBuyerOrderTotalTextView.text = data?.totalPrice.toString()
            binding.fragmentOrderDetailsForBuyerOrderStatusTextView.text = data?.orderStatus


        } // apply closed
    }// updateView closed


} // OrderDetailsForBuyerFragment closed