package com.example.farmersecom.features.profile.presentation.profile

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.load
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentProfileBinding
import com.example.farmersecom.features.profile.domain.model.Profile
import com.example.farmersecom.utils.constants.Constants.APP_PACKAGE_NAME
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasCameraPermission
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasStoragePermission
import com.example.farmersecom.utils.imageUtils.ImageCropHelper
import com.example.farmersecom.utils.imageUtils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import timber.log.Timber

import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
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

        initViews()
        subscribeProfileResponseFlow()
        subscribeChangeImageResponseFlow()
    } // onViewCreate closed

    override fun onStart()
    {
        super.onStart()
        if(viewModel.getAuthToken()==null)
        {
            findNavController().navigate(R.id.action_profileFragment_to_logInFragment)
        }
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
      //  imageViewProfile.load(data?.userImgUrl)
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
                findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
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
                    captureImage()
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
                    getImageFromGallery()
            }else
            {
                multiPermissionCallback.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }


            dialog.dismiss()
        } // dialog closed
        val alert = builder.create()
        alert.show()

    } // changPhoto



    private val multiPermissionCallback =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
            {
                    map ->
                //handle individual results if desired
                map.entries.forEach()
                { entry ->
                    when (entry.key)
                    {
                        Manifest.permission.READ_EXTERNAL_STORAGE-> if(entry.value)
                            {
                                getImageFromGallery()
                            }else
                            {
                                permissionDenied("App Needs Permission to Pick Image")
                            }
                        Manifest.permission.CAMERA -> if(entry.value)
                        {
                            captureImage()
                        }else
                        {
                            permissionDenied("App Needs Permission to Capture Image")
                        }
                    } // when closed

                } // forEach closed
            } // ActivityResult Contract closed


    private fun permissionDenied(msg:String)
    {
        requireContext().showToast(msg)
            startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply ()
            {
                data = Uri.fromParts("package", APP_PACKAGE_NAME, null)
            })
    }// permissionDenied


    /** Pick Gallery Image  and pass it to image cropper **/




    private fun getImageFromGallery()
    {
        selectImageFromGalleryResult.launch("image/*")
    } // getImageFromGallery closed



    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let()
        {
            binding.imageViewProfile.setImageURI(uri)
            // pass the uri to  image cropper
            cropGalleryImage.launch(uri)
        }
    } //

    private val cropGalleryImage = registerForActivityResult(ImageCropHelper.cropImage)
    {
        it?.let()
        { uri ->
            binding.imageViewProfile.setImageURI(uri)
            uploadImage(uri)
        }
    } // pickGalleryImage result closed


    /** Capture Image from Camera **/

    private var imageUri: Uri? = null

    private fun captureImage()
    {
        ImageUtils.createImageFile(requireContext())?.also()
        {
            imageUri = FileProvider
                .getUriForFile(requireContext(),
                    BuildConfig.APPLICATION_ID + ".fileprovider", it)
            takePictureRegistration.launch(imageUri)
        }
    } // getImageFrom Gallery closed

    /**
        Take picture from camera
        then
        pass it to image cropping library
    */
        private val takePictureRegistration =
            registerForActivityResult(ActivityResultContracts.TakePicture())
            { isSuccess ->
                if (isSuccess)
                {
                    binding.imageViewProfile.setImageURI(imageUri)
                    // picture taken -> now passing to image cropper
                    // launch image cropper activity
                    cropCapturedImage.launch(imageUri)
                }
            } // camera activity result closed



        private val  cropCapturedImage = registerForActivityResult(ImageCropHelper.cropImage)
        {
            it?.let()
            { uri ->
                binding.imageViewProfile.setImageURI(uri)

                uploadImage(uri)
            }
        } // cropCapturedImage closed





    private fun uploadImage(uri: Uri)
    {

//        val file =  File(uri.path!!)
//       val body =  MultipartBody.Builder().setType(MultipartBody.FORM)
//            .addFormDataPart("userImage", file.name,uri.toFile().asRequestBody())
//            .build()
//        viewModel.uploadUserImg(body)
//


        val file = uri.toFile();
        val requestFile = file.asRequestBody(requireContext().contentResolver.getType(uri)?.toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("userImage", file.path, requestFile)
        viewModel.uploadUserImg(body)

    } // uploadImage closed


    private fun subscribeChangeImageResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.uploadUserImgResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            Timber.tag(TAG).d("Loading")
                        }
                        is NetworkResource.Success ->
                        {
                            Timber.tag(TAG).d("${it.data}")

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



} // Profile Fragment closed

