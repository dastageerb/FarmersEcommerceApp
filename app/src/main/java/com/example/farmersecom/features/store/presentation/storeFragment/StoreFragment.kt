package com.example.farmersecom.features.store.presentation.storeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentStoreBinding
import com.example.farmersecom.features.store.domain.model.DashBoardItem
import com.example.farmersecom.utils.constants.Constants.TAG
import timber.log.Timber

class StoreFragment : BaseFragment<FragmentStoreBinding>()
{

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentStoreBinding
    {

       return FragmentStoreBinding.inflate(inflater,container,false)
    } // onCreateView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
            initView()
    }

    private fun initView()
    {
        val list = ArrayList<DashBoardItem>()
        list.add(DashBoardItem("Live Products",R.drawable.ic_baseline_live_orders_24))
        list.add(DashBoardItem("Discountinued Products", R.drawable.ic_baseline_discountinued_24))
        list.add(DashBoardItem("Orders", R.drawable.ic_baseline_live_orders_24))

        val adapter  = StoreDashBoardAdapter()
        {
            Timber.tag(TAG).d(it.itemName)
        }
        adapter.itemList = list
        binding.storeDashboardListView.adapter = adapter


    }

} // StoreFragment