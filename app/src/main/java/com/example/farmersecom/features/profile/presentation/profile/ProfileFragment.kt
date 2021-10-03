package com.example.farmersecom.features.profile.presentation.profile

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.akhbar.utils.NetworkResource
import com.example.akhbar.utils.ViewExtension.hide
import com.example.akhbar.utils.ViewExtension.load
import com.example.akhbar.utils.ViewExtension.show
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentProfileBinding
import com.example.farmersecom.features.profile.domain.model.Profile
import com.example.farmersecom.utils.Constants.TAG
import com.example.farmersecom.utils.ContextExtension.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() ,View.OnClickListener
{

    private val viewModel: ProfileViewModel by viewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentProfileBinding
    {
       return FragmentProfileBinding.inflate(inflater,container,false)
    } // onCreateView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        subscribeProfileResponseFlow()

    } // onViewCreate closed

    private fun subscribeProfileResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.userNetworkEntity.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            Timber.tag(TAG).d("${it.data}")
                            updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed

    } // subscribeProfileResponseFlow closed

    private fun initViews()
    {
        binding.buttonProfileFragLogout.setOnClickListener(this)
        binding.buttonUpdateInfo.setOnClickListener(this)
        binding.buttonSetupStore.setOnClickListener(this)
        binding.buttonGoToStore.setOnClickListener(this)
        binding.buttonChangePhoto.setOnClickListener(this)


        viewModel.getProfile()

    } /// init Views

    private fun updateViews(data: Profile?) = binding.apply()
    {
        imageViewProfile.load(data?.userImgUrl)
        textViewProfileFragName.text = data?.fullName
        if(data?.isSeller==true)
        {
            buttonSetupStore.hide()
            buttonGoToStore.show()
        }else
        {
            buttonSetupStore.show()
            buttonGoToStore.hide()
        }
    } // populateViewClosed

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.buttonProfileFragLogout ->
            {
                viewModel.clearToken()
                findNavController().navigate(R.id.action_profileFragment_to_logInFragment)
            }
            R.id.buttonSetupStore ->
            {
                findNavController().navigate(R.id.action_profileFragment_to_setupStoreFragment)
            }
            R.id.buttonGoToStore -> findNavController().navigate(R.id.action_profileFragment_to_storeFragment)
            R.id.buttonChangePhoto -> changePhoto();
        } // when closed
    } // onClick closed



    private val mPermissionResult = registerForActivityResult(RequestPermission())
    { isGranted ->

            if (isGranted)
            {

            } else
            {
                requireContext().showToast("App needs Permissions to Continue")
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", APP_PACKAGE_NAME, null)
                })
            } // else closed
        } /// forEach closed
    } // Request Permission closed



    private fun changePhoto()
    {
//        mPermissionResult.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }


} // Profile Fragment closed