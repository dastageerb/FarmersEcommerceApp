package com.example.farmersecom.features.profile.presentation.profile

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.BuildConfig
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentProfileBinding
import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.features.profile.presentation.ProfileViewModel
import com.example.farmersecom.utils.constants.Constants.APP_PACKAGE_NAME
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasCameraPermission
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasStoragePermission
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.imageUtils.ImageCropHelper
import com.example.farmersecom.utils.imageUtils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import timber.log.Timber

import okhttp3.RequestBody.Companion.asRequestBody


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
        setHasOptionsMenu(true)

        Timber.tag(TAG).d("token = ${viewModel.getAuthToken()}")
        if(viewModel.getAuthToken()?.isEmpty() == true)
        {
            findNavController().navigate(R.id.action_profileFragment_to_logInFragment)
        }else
        {
            initViews()
            subscribeProfileResponseFlow()
        }

    } // onViewCreate closed

    override fun onStart()
    {
        super.onStart()

    }


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
                        is NetworkResource.Loading ->
                        {
                            binding.fragmentProfileProgressBar.show()
                            Timber.tag(TAG).d("Loading")
                        }
                        is NetworkResource.Success ->
                        {
                            binding.fragmentProfileProgressBar.hide()
                            Timber.tag(TAG).d(""+it.data)
                            updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {

                            binding.fragmentProfileProgressBar.hide()
                            requireContext().showToast(it.msg.toString())
                            Timber.tag(TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed

    } // subscribeProfileResponseFlow closed

    private fun initViews()
    {
        binding.fragmentProfileFullProfileButton.setOnClickListener(this)
        binding.buttonSetupStore.setOnClickListener(this)
        binding.buttonGoToStore.setOnClickListener(this)
        binding.buttonBuyersSection.setOnClickListener(this)
        binding.buttonCommunitySection.setOnClickListener(this)

        viewModel.getProfile()

    } /// init Views

    private fun updateViews(data: ProfileNetworkEntity?) = binding.apply()
    {

        imageViewProfile.load(data?.userImgUrl,R.drawable.ic_baseline_profile_24)
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

        binding.fragmentProfileDetailsLayout.show()

    } // populateViewClosed

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.fragmentProfileFullProfileButton ->
            {
                findNavController().navigate(R.id.action_profileFragment_to_fullUserProfileFragment)
            }

            R.id.buttonSetupStore ->
            {
                findNavController().navigate(R.id.action_profileFragment_to_setupStoreFragment)
            }
            R.id.buttonGoToStore -> findNavController().navigate(R.id.action_profileFragment_to_storeFragment)
            R.id.buttonBuyersSection ->
            {
                findNavController().navigate(R.id.action_profileFragment_to_buyerDashboardFragment)
            }
            R.id.buttonCommunitySection -> findNavController().navigate(R.id.action_profileFragment_to_communityContributionsFragment)
        } // when closed
    } // onClick closed







    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_logout,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.menu_logout ->
            {
                viewModel.clearToken()
                viewModel.clearCartOnLogout()
                viewModel.clearFiltersOnLogOut()
                viewModel.clearUser()
                findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                this.onDestroy()
            }
        }
        return super.onOptionsItemSelected(item)
    }



} // Profile Fragment closed

