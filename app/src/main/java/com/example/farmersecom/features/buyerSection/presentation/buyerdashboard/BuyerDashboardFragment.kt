package com.example.farmersecom.features.buyerSection.presentation.buyerdashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentBuyerDashboardBinding
import com.example.farmersecom.features.storeAdmin.domain.model.DashBoardItem
import com.example.farmersecom.features.storeAdmin.presentation.storeDashboard.StoreDashBoardAdapter
import com.example.farmersecom.utils.constants.Constants
import timber.log.Timber

class BuyerDashboardFragment : BaseFragment<FragmentBuyerDashboardBinding>()
{


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentBuyerDashboardBinding
    {
        return  FragmentBuyerDashboardBinding.inflate(inflater,container,false)
    } // onCreateView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    } // onViewCreated closed

    private fun initViews()
    {
        val list = ArrayList<DashBoardItem>()
        list.add(DashBoardItem(getString(R.string.order_history),R.drawable.ic_baseline_history_24))
        list.add(DashBoardItem(getString(R.string.current_orders), R.drawable.ic_baseline_active_actions_24))
        list.add(DashBoardItem(getString(R.string.cart), R.drawable.ic_baseline_cart_bag_24))

        val adapter  = StoreDashBoardAdapter()
        {
            navigateFromDashBoard(it)
          //  Timber.tag(Constants.TAG).d(it.itemName)
        }
        adapter.itemList = list
        binding.fragmentBuyerDashboardGridView.adapter = adapter

    } // initViews closed

    private fun navigateFromDashBoard(position: Int)
    {
        when(position)
        {
            0 -> findNavController().navigate(R.id.action_buyerDashboardFragment_to_buyerOrderHistoryFragment)
            1-> findNavController().navigate(R.id.action_buyerDashboardFragment_to_currentOrdersFragment)
            2 -> findNavController().navigate(R.id.action_buyerDashboardFragment_to_cartFragment)

        }
    }


} // BuyerDashBoardFragment closed