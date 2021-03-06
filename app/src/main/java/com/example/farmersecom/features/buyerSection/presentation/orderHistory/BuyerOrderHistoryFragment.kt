package com.example.farmersecom.features.buyerSection.presentation.orderHistory

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentBuyerOrderHistoryBinding
import com.example.farmersecom.features.buyerSection.presentation.BuyerDashboardViewModel
import com.example.farmersecom.features.buyerSection.presentation.OrderStatusAdapter
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class BuyerOrderHistoryFragment : BaseFragment<FragmentBuyerOrderHistoryBinding>()
{


    private val viewModel:BuyerDashboardViewModel by activityViewModels()
    private lateinit var orderStaAdapter: OrderStatusAdapter


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentBuyerOrderHistoryBinding
    {
        return FragmentBuyerOrderHistoryBinding.inflate(inflater,container,false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        setupRecycler(binding.fragmentBuyerOrderHistoryRecyclerView)

//         get orders which are not active  = completed
        viewModel.getBuyerOrders(false)
        subscribeToBuyerCurrentOrdersResponseFlow()
    } // onViewCreated closed

    private fun subscribeToBuyerCurrentOrdersResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.buyerOrderResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.fragmentOrderHistoryProgressBar.show()
                        }
                        is NetworkResource.Success ->
                        {
                            binding.fragmentOrderHistoryProgressBar.hide()
                            if(it.data?.orders.isNullOrEmpty())
                            {
                                requireContext().showToast(getString(R.string.no_completed_orders_yet))
                            }
                            Timber.tag(Constants.TAG).d("${it.data}")
                            orderStaAdapter.submitList(it.data?.orders)
                        }
                        is NetworkResource.Error ->
                        {
                            binding.fragmentOrderHistoryProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeToBuyerCurrentOrderFlow

    private fun setupRecycler(recycler: RecyclerView)
    {
        orderStaAdapter = OrderStatusAdapter()
        {
            viewModel.setOrderId(it)
            findNavController().navigate(R.id.action_buyerOrderHistoryFragment_to_orderDetailsForBuyerFragment)
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = orderStaAdapter
    } // setupHomeSlider closed



}