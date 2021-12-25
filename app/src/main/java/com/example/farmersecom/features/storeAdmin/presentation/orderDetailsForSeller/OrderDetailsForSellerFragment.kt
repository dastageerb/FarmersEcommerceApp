package com.example.farmersecom.features.storeAdmin.presentation.orderDetailsForSeller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentOrderDetailsForSellerBinding
import com.example.farmersecom.features.buyerSection.domain.model.orderDetails.OrderDetailsResponse
import com.example.farmersecom.features.storeAdmin.presentation.StoreDashboardViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


class OrderDetailsForSellerFragment : BaseFragment<FragmentOrderDetailsForSellerBinding>()
{


    private val  viewModel: StoreDashboardViewModel by activityViewModels()
    val orderStatusList = arrayOf("pending","inProcess","ship","complete")
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentOrderDetailsForSellerBinding
    {
        return FragmentOrderDetailsForSellerBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        binding.fragmentOrderDetailsForSellerButtonChangStatus.setOnClickListener()
        {
                changeStatus()
        }

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

        subscribeToOrderDetails()
        subscribeToStatusChange()

    } // onViewCreated

    private fun changeStatus() = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
    {
        val status = binding.fragmentOrderDetailsForSellerButtonChangStatus.text.toString()
        orderStatusList.forEachIndexed()
        {
            index,sts ->
            if(status == sts)
            {
                viewModel.getOrderId.collect()
                {
                    Timber.tag(TAG).d(""+it )
                    viewModel.changeOrderStatus(status,it)
                    delay(500)
                    getOrderDetails(it)
                }
            }
        }

    } // changeStatus


    private fun getOrderDetails(orderId: String)
    {
        viewModel.orderDetails(orderId)

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
                            binding.orderDetailsForSellerFragmentProgressBar.show()
                            binding.fragmentOrderDetailsForSellerDetailsLayout.hide()
                        }
                        is NetworkResource.Success ->
                        {
                            binding.orderDetailsForSellerFragmentProgressBar.hide()
                            binding.fragmentOrderDetailsForSellerDetailsLayout.show()
                            Timber.tag(Constants.TAG).d("${it.data}")
                            updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            binding.orderDetailsForSellerFragmentProgressBar.show()
                            binding.fragmentOrderDetailsForSellerDetailsLayout.hide()
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } //  subscribeToOrderDetails


    private fun subscribeToStatusChange()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.statusMsgResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                           // binding.orderDetailsForSellerFragmentProgressBar.show()
                        }
                        is NetworkResource.Success ->
                        {
                          //  binding.orderDetailsForSellerFragmentProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.data?.message}")
                            //updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            binding.orderDetailsForSellerFragmentProgressBar.show()
                            binding.fragmentOrderDetailsForSellerDetailsLayout.hide()
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
            // buyer info
            binding.fragmentOrderDetailsForSellerBuyerNameTextView.text = data?.name
            binding.fragmentOrderDetailsForSellerPostalCodeTextView.text = data?.postalCode.toString()
            binding.fragmentOrderDetailsForSellerCityTextView.text = data?.city
            binding.fragmentOrderDetailsForSellerAddressTextView.text = data?.orderAddress
            binding.fragmentOrderDetailsForSellerContactNoTextView.text =data?.contactNumber

            // product info
            binding.fragmentOrderDetailsForSellerProductNameTextView.text = data?.productName
            binding.fragmentOrderDetailsForSellerProductDefaultQuantityTextView.text  = data?.productQuantity.toString()
            binding.fragmentOrderDetailsForSellerProductDPriceTextView.text = data?.productprice.toString()

            // Order info
            binding.fragmentOrderDetailsForSellerOrderIdTextView.text = data?.id
            binding.fragmentOrderDetailsForSellerOderDateTextView.text = data?.date?.substring(0,10)
            binding.fragmentOrderDetailsForSellerOrderQuantityTextView.text = data?.orderQuantity.toString()
            binding.fragmentOrderDetailsForSellerOrderDeliveryChargesTextView.text = data?.deliveryCharges.toString()

            binding.fragmentOrderDetailsForSellerOrderSubtotalTextView.text = data?.subTotal.toString()
            binding.fragmentOrderDetailsForSellerOrderTotalTextView.text = data?.totalPrice.toString()
            binding.fragmentOrderDetailsForSellerOrderStatusTextView.text = data?.orderStatus


            orderStatusList.forEachIndexed()
            {
                index: Int, status: String ->
                if(data?.orderStatus.equals(status) && index < orderStatusList.size-1)
                {
                    binding.fragmentOrderDetailsForSellerButtonChangStatus.text = orderStatusList.get(index+1)
                } // if closed
            }

        } // apply closed
    }// updateView closed



} // OrderDetailsForSellerFragment closed