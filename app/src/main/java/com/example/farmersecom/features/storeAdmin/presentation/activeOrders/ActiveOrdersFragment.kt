package com.example.farmersecom.features.storeAdmin.presentation.activeOrders

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentActiveOrdersBinding
import com.example.farmersecom.features.buyerSection.presentation.BuyerDashboardViewModel
import com.example.farmersecom.features.buyerSection.presentation.OrderStatusAdapter
import com.example.farmersecom.features.storeAdmin.presentation.StoreDashboardViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@InternalCoroutinesApi
@AndroidEntryPoint
class ActiveOrdersFragment :BaseFragment<FragmentActiveOrdersBinding>()
{


    private lateinit var orderStaAdapter: OrderStatusAdapter
    private val  viewModel: StoreDashboardViewModel by activityViewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentActiveOrdersBinding
    {
        return FragmentActiveOrdersBinding.inflate(inflater,container,false);
    } // createView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler(binding.fragmentActiveOrdersRecyclerView)
        viewModel.getSellerOrders(true)
        subscribeToBuyerCurrentOrdersResponseFlow()

    } // onViewCreated closed


    private fun subscribeToBuyerCurrentOrdersResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.sellerOrderResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {

                        }
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d("${it.data}")
                            orderStaAdapter.submitList(it.data?.orders)
                            // updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("fail -> ${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeToSearchResponseFlow


    private fun setupRecycler(recycler: RecyclerView)
    {
        orderStaAdapter = OrderStatusAdapter()
        {
            viewModel.setOrderId(it)
            findNavController().navigate(R.id.action_activeOrdersFragment_to_orderDetailsForSellerFragment)
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = orderStaAdapter
    } // setupHomeSlider closed


} // ActiveOrdersFragment closed