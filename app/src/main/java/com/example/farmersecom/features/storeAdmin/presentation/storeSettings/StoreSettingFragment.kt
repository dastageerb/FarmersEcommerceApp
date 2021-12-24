package com.example.farmersecom.features.storeAdmin.presentation.storeSettings

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentStoreSettingBinding
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class StoreSettingFragment : BaseFragment<FragmentStoreSettingBinding>()
{

    private val storeSettingViewModel:StoreSettingViewModel by viewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentStoreSettingBinding
    {
        return FragmentStoreSettingBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


        subscribeToStoreDetailsResponse()
        subscribeToStatusChangeResponse()
        subscribeToImageChangedResponse()

    } // onView created closed


    private fun subscribeToStoreDetailsResponse()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                storeSettingViewModel.productStoreResponse.collect()
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
                           // Timber.tag(Constants.TAG).d("${it.data?.messege}")
                            //updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscriber closed


    private fun subscribeToImageChangedResponse()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                storeSettingViewModel.storeImageChangedResponse.collect()
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
                            // Timber.tag(Constants.TAG).d("${it.data?.messege}")
                            //updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscriber closed



    private fun subscribeToStatusChangeResponse()
    {
        // when store details updated
        // when store delivery method updated


        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                storeSettingViewModel.productStoreResponse.collect()
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
                            // Timber.tag(Constants.TAG).d("${it.data?.messege}")
                            //updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscriber closed

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_edit,menu)
    } // onCreateOptionsMenu closed

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.menu_edit ->
            {
                binding.fragmentStoreSettingInfoLayout.hide()
                binding.fragmentStoreSettingUpdateStoreLayout.show()
            } //
        } // when closed
        return super.onOptionsItemSelected(item)
    } // onOptionsItemsSelected closed


} // StoreSettingFragment closed