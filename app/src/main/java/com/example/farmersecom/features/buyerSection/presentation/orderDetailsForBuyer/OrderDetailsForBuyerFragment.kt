package com.example.farmersecom.features.buyerSection.presentation.orderDetailsForBuyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
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
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


class OrderDetailsForBuyerFragment : BaseFragment<FragmentOrderDetailsForBuyerBinding>() , View.OnClickListener
{


    private val viewModel: BuyerDashboardViewModel by activityViewModels()
    private lateinit var productId:String


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


        binding.fragmentOrderDetailsForBuyerRatingBar.rating = 0.5f
        binding.fragmentOrderDetailsForBuyerRatingBar.setOnRatingBarChangeListener()
        { ratingBar, rating, fromUser ->

        }

        binding.fragmentOrderDetailsForBuyerRateButton.setOnClickListener(this)

        subscribeToStatusMessageResponses()

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
        productId = data?.productId!!

        binding.apply()
        {



            if(data?.orderStatus.equals("completed"))
            {
                binding.fragmentOrderDetailsForBuyerRatingLayout.show()
            }

            // store info
            binding.fragmentOrderDetailsForBuyerStoreNameTextView.text = data?.storeName
            // product info
            binding.fragmentOrderDetailsForBuyerProductNameTextView.text = data?.productName
            binding.fragmentOrderDetailsForBuyerProductDefaultQuantityUnitTextView.text =data?.productQuantity.toString()
            binding.fragmentOrderDetailsForBuyerProductDPriceTextView.text = data?.productprice.toString()
            binding.fragmentOrderDetailsForBuyerProductDefaultQuantityUnitTextView.text = data?.unit
            // orderInfo
            binding.fragmentOrderDetailsForBuyerOrderIdTextView.text = data?.id
            binding.fragmentOrderDetailsForBuyerOrderQuantityTextView.text = data?.orderQuantity.toString()
            binding.fragmentOrderDetailsForBuyerOderDateTextView.text = data?.date?.substring(0,10)
            binding.fragmentOrderDetailsForBuyerOrderDeliveryChargesTextView.text = data?.deliveryCharges.toString()
            binding.fragmentOrderDetailsForBuyerOrderSubtotalTextView.text = data?.subTotal.toString()
            binding.fragmentOrderDetailsForBuyerOrderTotalTextView.text = data?.totalPrice.toString()
            binding.fragmentOrderDetailsForBuyerOrderStatusTextView.text = data?.orderStatus

        } // apply closed
    }// updateView closed


    private fun subscribeToStatusMessageResponses()
    {
        // Same method used for cancel order or rate product response

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.statusMsgResponse.collect()
                {
                    when (it)
                    {
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d("${it.data}")
                            binding.progressBarFragmentOrderDetailsForBuyer.hide()
                            requireContext().showToast(it.data?.message.toString())
                        }
                        is NetworkResource.Error ->
                        {

                            binding.progressBarFragmentOrderDetailsForBuyer.hide()
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                        is NetworkResource.Loading ->
                        {
                            binding.progressBarFragmentOrderDetailsForBuyer.show()
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.fragmentOrderDetailsForBuyerRateButton -> rateProduct()
        } // when closed

    } // onClick closed

    private fun rateProduct()
    {
        if(!this::productId.isInitialized)
        {
            requireContext().showToast("Order Details are not loaded yet")
        }else
        {
            val rating = binding.fragmentOrderDetailsForBuyerRatingBar.rating
            viewModel.rateProduct(productId,rating)
        }//
    } // rateProduct


} // OrderDetailsForBuyerFragment closed