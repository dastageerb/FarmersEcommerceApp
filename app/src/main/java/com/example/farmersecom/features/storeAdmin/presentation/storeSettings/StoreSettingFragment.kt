package com.example.farmersecom.features.storeAdmin.presentation.storeSettings

import android.os.Bundle
import android.view.*
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentStoreSettingBinding
import com.example.farmersecom.features.productStore.domain.model.storeDetails.StoreDetailsResponse
import com.example.farmersecom.features.profile.data.framework.entities.SetupStoreData
import com.example.farmersecom.features.storeAdmin.domain.model.updateStore.UpdateStore
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class StoreSettingFragment : BaseFragment<FragmentStoreSettingBinding>() , View.OnClickListener, CompoundButton.OnCheckedChangeListener
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

        storeSettingViewModel.getStoreDetails()
        subscribeToStoreDetailsResponse()
        subscribeToStatusResponses()
        subscribeToImageChangedResponse()


        binding.fragmentStoreSettingsDeliveryInfoSwitch.setOnCheckedChangeListener(this)
        binding.fragmentStoreSettingEditStoreButton.setOnClickListener(this)
        binding.fragmentStoreSettingsStoreUpdateButton.setOnClickListener(this)


    } // onView created closed


    private fun subscribeToStatusResponses()
    {

        // when store details updated
        // when store delivery method updated
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                storeSettingViewModel.getStatusMsgResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.bindingStoreSettingProgressBar.show()
                            Timber.tag(Constants.TAG).d("loading")
                        }
                        is NetworkResource.Success ->
                        {
                            binding.bindingStoreSettingProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.data?.message}")
                            //updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            binding.bindingStoreSettingProgressBar.hide()
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
                           binding.bindingStoreSettingProgressBar.show()
                            binding.fragmentStoreSettingUpdateStoreLayout.hide()

                        }
                        is NetworkResource.Success ->
                        {
                            binding.bindingStoreSettingProgressBar.hide()
                            binding.fragmentStoreSettingInfoLayout.show()
                            Timber.tag(TAG).d(""+it.data)
                            updateViews(it.data)
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


    private fun updateViews(data: StoreDetailsResponse?)
    {

        binding.fragmentStoreSettingStoreImageImageView.load(data?.storeImage)

        binding.fragmentStoreSettingStoreNameTextView.text = data?.storeName
        binding.fragmentStoreSettingStoreDescriptionTextView.text = data?.about

        binding.fragmentStoreSettingsDeliveryInfoSwitch.isChecked = data?.deliversOfOutCity!!
        if(data.deliversOfOutCity)
        {
            binding.fragmentStoreSettingsDeliveryInfoSwitch.text = getString(R.string.yes)
        }else
        {
            binding.fragmentStoreSettingsDeliveryInfoSwitch.text = getString(R.string.no)
        }

        binding.fragmentStoreSettingStoreNameEditText.setText( data?.storeName)
        binding.fragmentStoreSettingStoreDescriptionEditText.setText(data?.about)

        binding.fragmentStoreSettingInfoLayout.show()

    } // updateStoreViews




    /** onClick listener**/
    override fun onClick(v: View?)
    {

        when(v?.id)
        {
            R.id.fragmentStoreSettingEditStoreButton ->
            {
                binding.fragmentStoreSettingInfoLayout.hide()
                binding.fragmentStoreSettingUpdateStoreLayout.show()
            } //
            R.id.fragmentStoreSettingsStoreUpdateButton ->
            {
                updateStore()
            }

        } // when closed

    } // onClick closed

    private fun updateStore()
    {

        val name = binding.fragmentStoreSettingStoreNameEditText.text.toString().trim()
        val description = binding.fragmentStoreSettingStoreDescriptionEditText.text.toString().trim()


        if(validation(name,description))
        {
            storeSettingViewModel.updateStore(name,description)
        } // if closed


    } // setupStore


    private fun validation(name:String,description:String): Boolean
    {
        return name.nonEmpty { binding.fragmentStoreSettingStoreNameEditText.error = it }
                &&
                description.nonEmpty { binding.fragmentStoreSettingStoreDescriptionEditText.error = it }

    } // validation

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean)
    {
        if(buttonView?.isPressed!!)
        {
            storeSettingViewModel.updateStoreDelivery(isChecked)
            if(isChecked)
            {
                binding.fragmentStoreSettingsDeliveryInfoSwitch.text = getString(R.string.yes)
            }else
            {
                binding.fragmentStoreSettingsDeliveryInfoSwitch.text = getString(R.string.no)
            } // else closed

        } // if closed

    } // onCheckedChanged closed


} // StoreSettingFragment closed