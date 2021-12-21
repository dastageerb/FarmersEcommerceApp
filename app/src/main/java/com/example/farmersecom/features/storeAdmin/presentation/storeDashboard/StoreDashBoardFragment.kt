package com.example.farmersecom.features.storeAdmin.presentation.storeDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentStoreDashboardBinding
import com.example.farmersecom.features.storeAdmin.domain.model.DashBoardItem
import com.example.farmersecom.utils.constants.Constants.TAG
import timber.log.Timber


class StoreDashBoardFragment : BaseFragment<FragmentStoreDashboardBinding>(),View.OnClickListener
{

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentStoreDashboardBinding
    {

       return FragmentStoreDashboardBinding.inflate(inflater,container,false)
    } // onCreateView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
            initViews()
    } // onViewCreated closed

    private fun initViews()
    {
        val list = ArrayList<DashBoardItem>()
        list.add(DashBoardItem("Active Products",R.drawable.ic_baseline_live_orders_24))
        list.add(DashBoardItem("Discontinued Products", R.drawable.ic_baseline_discountinued_24))
        list.add(DashBoardItem("Active Order", R.drawable.ic_baseline_live_orders_24))
        list.add(DashBoardItem("Completed Order", R.drawable.ic_baseline_live_orders_24))
        val adapter  = StoreDashBoardAdapter()
        {
            navigateFromDashBoard(it)
        }
        adapter.itemList = list
        binding.storeDashboardListView.adapter = adapter

        binding.fragmentStoreDashboardAddNewProductButton.setOnClickListener(this)

    } // initViews closed

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.fragmentStoreDashboardAddNewProductButton -> findNavController().navigate(R.id.action_storeFragment_to_addNewProductFragment)
        } // when closed
    } // onClick closed


    private fun navigateFromDashBoard(position: Int)
    {
        when(position)
        {
            0 -> findNavController().navigate(R.id.action_storeFragment_to_activeProductsFragment)
            1 -> findNavController().navigate(R.id.action_storeFragment_to_discontinuedProductsFragment)
            2 -> findNavController().navigate(R.id.action_storeFragment_to_activeOrdersFragment)
            3 -> findNavController().navigate(R.id.action_storeFragment_to_completedOrdersFragment)
        }
    }


} // StoreFragment