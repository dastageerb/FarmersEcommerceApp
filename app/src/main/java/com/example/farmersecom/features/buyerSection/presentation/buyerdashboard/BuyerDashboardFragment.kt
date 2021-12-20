package com.example.farmersecom.features.buyerSection.presentation.buyerdashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        list.add(DashBoardItem("Order History",R.drawable.ic_baseline_live_orders_24))
        list.add(DashBoardItem("Current Orders", R.drawable.ic_baseline_discountinued_24))
        list.add(DashBoardItem("Favourites", R.drawable.ic_baseline_live_orders_24))
        list.add(DashBoardItem("Notification", R.drawable.ic_baseline_live_orders_24))


        val adapter  = StoreDashBoardAdapter()
        {
            Timber.tag(Constants.TAG).d(it.itemName)
        }
        adapter.itemList = list
        binding.fragmentBuyerDashboardGridView.adapter = adapter

    } // initViews closed


} // BuyerDashBoardFragment closed