package com.example.farmersecom.features.profile.presentation.setupStore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentSetupStoreBinding
import com.example.farmersecom.features.profile.data.framework.entities.SetupStoreData
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SetupStoreFragment : BaseFragment<FragmentSetupStoreBinding>()
{

    private val viewModel:SetupStoreViewModel by viewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentSetupStoreBinding
    {
        return FragmentSetupStoreBinding.inflate(inflater,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSetupStore.setOnClickListener()
        {
            setupStore();
        }

        binding.fragmentStoreSetupDeliveryInfoSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener
        { _, isChecked ->

            if(isChecked)
            {
                binding.fragmentStoreSetupDeliveryInfoSwitch.text = getString(R.string.yes)

                binding.fragmentSetupStoreCodNote.show()

            }else
            {
                binding.fragmentSetupStoreCodNote.hide()
                binding.fragmentStoreSetupDeliveryInfoSwitch.text = getString(R.string.no)
            }
        })


        subscribeSetupResponse();

    } // onView Created

    private fun subscribeSetupResponse()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.setupStoreResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.progressBarSetupStoreFrag.show()
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Error ->
                        {
                            binding.progressBarSetupStoreFrag.hide()
                            requireContext().showToast("error: ${it.msg.toString()}")
                            Timber.tag(Constants.TAG).d("error: " + it.msg.toString())
                        }
                        is NetworkResource.Success ->
                        {
                            requireContext().showToast(it.data?.message.toString())
                            binding.progressBarSetupStoreFrag.hide()
                            Timber.tag(Constants.TAG).d(it.data.toString())
                            findNavController().navigate(R.id.action_setupStoreFragment_to_storeFragment)
                        }
                        else -> {}
                    } // when closed
                } // collect closed
            } // repeatOnLifeCycle closed
        } // lifeCycleScope closed



    } // subscribe setupResponse

    private fun setupStore()
    {
        val name = binding.editTextStoreName.text.toString().trim()
        val description = binding.editTextStoreDescription.text.toString().trim()
        val deliversOutOfCity = binding.fragmentStoreSetupDeliveryInfoSwitch.isChecked


        if(validation(name,description))
        {
            val storeData = SetupStoreData(description,name,deliversOutOfCity)
            viewModel.setupStore(storeData)
        } // if closed


    } // setupStore


    private fun validation(name:String,description:String): Boolean
    {
        return name.nonEmpty { binding.editTextStoreName.error = it }
                    &&
               description.nonEmpty { binding.editTextStoreDescription.error = it }

    } // validation

} // SetupStore Fragment