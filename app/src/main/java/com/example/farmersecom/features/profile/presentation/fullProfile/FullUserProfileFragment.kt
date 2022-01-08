package com.example.farmersecom.features.profile.presentation.fullProfile

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.BuildConfig
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentFullUserProfileBinding
import com.example.farmersecom.features.profile.domain.model.UserInfoResponse.UserInfoResponse
import com.example.farmersecom.features.profile.presentation.ProfileViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasCameraPermission
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasStoragePermission
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.imageUtils.ImageCropHelper
import com.example.farmersecom.utils.imageUtils.ImageUtils
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber

@AndroidEntryPoint
class FullUserProfileFragment : BaseFragment<FragmentFullUserProfileBinding>() , View.OnClickListener
{

    private val viewModel: ProfileViewModel by activityViewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentFullUserProfileBinding
    {
        return FragmentFullUserProfileBinding.inflate(inflater,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initViews()
        viewModel.getFullUserProfile()
        subscribeProfileResponseFlow()
        subscribeChangeImageResponseFlow()

    } // onViewCreated closed

    private fun initViews()
    {
        binding.fragmentFullUserProfileChangePasswordButton.setOnClickListener(this)
        binding.fragmentFullUserProfileEditPersonalInfoButton.setOnClickListener(this)
        binding.fragmentFullUserProfileChangPhotoImageView.setOnClickListener(this)
        binding.fragmentFullUserProfileChangeLanguageButton.setOnClickListener(this)

    } // initViews closed



    private fun subscribeProfileResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.userFullProfileResponse.collect()
                {
                    when (it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.fragmentFullUserProfileProgressBar.show()
                            Timber.tag(Constants.TAG).d("Loading")

                        }
                        is NetworkResource.Success ->
                        {
                            binding.fragmentFullUserProfileProgressBar.hide()
                            updateViews(it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            binding.fragmentFullUserProfileProgressBar.hide()
                            requireContext().showToast(it.msg.toString())
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscribeToFullProfileResponse closed

    private fun updateViews(data: UserInfoResponse?)
    {
        viewModel.userInfoResponse = data

        binding.apply()
        {

            fragmentFullUserProfileImageViewProfile.load(data?.userImgUrl,R.drawable.ic_baseline_profile_24)

            fragmentFullUserProfileFirstNameTextView.text = data?.firstName
            fragmentFullUserProfileLastNameTextView.text = data?.lastName
            fragmentFullUserProfileGenderTextView.text = data?.gender
            fragmentFullUserProfileEmailTextView.text = data?.email
            fragmentFullUserProfileContactTextView.text = data?.contactNumber
            fragmentFullUserProfileDateOfBirthTextView.text = data?.dateOfBirth
            fragmentFullUserProfileAddressTextView.text = data?.address
            fragmentFullUserProfileCityTextView.text = data?.city
            fragmentFullUserProfilePostalCodeTextView.text = data?.postalCode.toString()


           layoutFullUserProfileDetails.show()
        } //



    } // updateView closed


    override fun onClick(v: View?)
    {
           when(v?.id)
           {
               R.id.fragmentFullUserProfileChangPhotoImageView ->
               {
                   changePhoto()
               }
               R.id.fragmentFullUserProfileChangePasswordButton ->
               {
                   findNavController().navigate(R.id.action_fullUserProfileFragment_to_changePasswordFragment)
               }
               R.id.fragmentFullUserProfileEditPersonalInfoButton ->
               {
                   findNavController().navigate(R.id.action_fullUserProfileFragment_to_editPersonalInfoFragment)
               }
               R.id.fragmentFullUserProfileChangeLanguageButton ->
               {
                   findNavController().navigate(R.id.action_fullUserProfileFragment_to_changeLanguageFragment)

               }
           } //
    } // onClick closed



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
                            binding.fragmentFullUserProfileProgressBar.show()
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Success ->
                        {
                            binding.fragmentFullUserProfileProgressBar.hide()
                            binding.fragmentFullUserProfileImageViewProfile.load(it.data?.userImgUrl,R.drawable.ic_baseline_profile_24)
                            Timber.tag(Constants.TAG).d("${it.data}")
                        }
                        is NetworkResource.Error ->
                        {
                            requireContext().showToast(it.msg.toString())
                            binding.fragmentFullUserProfileProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed

    } // subscribeProfileResponseFlow closed


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
        startActivity(
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply ()
        {
            data = Uri.fromParts("package", Constants.APP_PACKAGE_NAME, null)
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
            binding.fragmentFullUserProfileImageViewProfile.load(uri.toString())
            // pass the uri to  image cropper
            cropGalleryImage.launch(uri)
        }
    } //

    private val cropGalleryImage = registerForActivityResult(ImageCropHelper.cropImage)
    {
        it?.let()
        { uri ->
            binding.fragmentFullUserProfileImageViewProfile.load(uri.toString())
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

                binding.fragmentFullUserProfileImageViewProfile.load(imageUri.toString())
                // picture taken -> now passing to image cropper
                // launch image cropper activity
                cropCapturedImage.launch(imageUri)
            }
        } // camera activity result closed



    private val  cropCapturedImage = registerForActivityResult(ImageCropHelper.cropImage)
    {
        it?.let()
        { uri ->

            binding.fragmentFullUserProfileImageViewProfile.load(uri.toString())
            uploadImage(uri)
        } // uri closed
    } // cropCapturedImage closed





    private fun uploadImage(uri: Uri)
    {


        val file = uri.toFile();
        val requestFile = file.asRequestBody(requireContext().contentResolver.getType(uri)?.toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("userImage", file.path, requestFile)

        viewModel.uploadUserImg(body)

    } // uploadImage closed


} //