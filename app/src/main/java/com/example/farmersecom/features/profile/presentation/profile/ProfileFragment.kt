package com.example.farmersecom.features.profile.presentation.profile

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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
import com.example.farmersecom.utils.Constants.APP_PACKAGE_NAME
import com.example.farmersecom.utils.Constants.TAG
import com.example.farmersecom.utils.ContextExtension.showToast
import com.example.farmersecom.utils.Permissions.hasCameraPermission
import com.example.farmersecom.utils.Permissions.hasStoragePermission
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


    /**Changing Image by camera or gallery **/

    private fun changePhoto()
    {
        val builder = AlertDialog.Builder(requireContext())
        // builder.setMessage("")
        builder.setTitle("Choose Image")
        builder.setCancelable(true)
        builder.setNegativeButton("Camera")
        { dialog, _ -> dialog.cancel()

            if(requireContext().hasCameraPermission())
            {

            }else
            {
                multiPermissionCallback.launch(arrayOf(Manifest.permission.CAMERA))
            }


            dialog.dismiss()
        }
        builder.setPositiveButton("Gallery")
        { dialog, _ ->

            if(requireContext().hasStoragePermission())
            {

            }else
            {
                multiPermissionCallback.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }


            dialog.dismiss()
        } // dialog closed
        val alert = builder.create()
        alert.show()

    } // changPhoto


//
//    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
//        if (isSuccess) {
//            latestTmpUri?.let { uri ->
//                previewImage.setImageURI(uri)
//            }
//        }
//    }
//
//    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        uri?.let { previewImage.setImageURI(uri) }
//    }



    private val multiPermissionCallback =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
            {
                    map ->
                //handle individual results if desired
                map.entries.forEach()
                { entry ->
                    when (entry.key)
                    {
                        Manifest.permission.READ_EXTERNAL_STORAGE->
                            requireContext().showToast("Read "+entry.value)
                        Manifest.permission.CAMERA -> requireContext().showToast("Camera "+entry.value)
                    } // when closed

                } // forEach closed
            } // ActivityResult Contract closed





//    private val capturePhotoPermission
//        = registerForActivityResult(ActivityResultContracts.RequestPermission())
//    {
//            isGranted ->
//            if (isGranted)
//            {
//                    captureImage();
//            } else
//            {
//                requireContext().showToast("App needs Permissions to Capture Photo")
//                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
//                    data = Uri.fromParts("package", APP_PACKAGE_NAME, null)
//                })
//            } // else closed
//        } /// forEach closed
//
//    private val accessGalleryPermission
//            = registerForActivityResult(ActivityResultContracts.RequestPermission())
//    {
//            isGranted ->
//        if (isGranted)
//        {
//            getImageFromGallery()
//        } else
//        {
//            requireContext().showToast("App needs Permissions to Access Gallery ")
//            startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
//                data = Uri.fromParts("package", APP_PACKAGE_NAME, null)
//            })
//        } // else closed
//    } /// forEach closed


    private fun getImageFromGallery()
    {

    } // getImageFromGallery closed


    private fun captureImage()
    {

    } // getImageFrom Gallery closed




} // Profile Fragment closed

