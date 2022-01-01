package com.example.farmersecom.features.communitySection.presentation.addPost

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import com.example.farmersecom.BuildConfig
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentAddPostBinding
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasCameraPermission
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasStoragePermission
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.imageUtils.ImageCropHelper
import com.example.farmersecom.utils.imageUtils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody


@AndroidEntryPoint
class AddPostFragment : BaseFragment<FragmentAddPostBinding>()
{


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentAddPostBinding
    {
        return  FragmentAddPostBinding.inflate(inflater,container,false)
    } // createView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

    } // onViewCreated



    private fun addPostImage()
    {
        val builder = AlertDialog.Builder(requireContext())
        // builder.setMessage("")
        builder.setTitle(getString(R.string.choose_image))
        builder.setCancelable(true)
        builder.setNegativeButton(getString(R.string.camera))
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
            // binding.fragmentStoreSettingStoreImageImageView.load(uri.toString())
            // pass the uri to  image cropper
            cropGalleryImage.launch(uri)
        }
    } //

    private val cropGalleryImage = registerForActivityResult(ImageCropHelper.cropImage)
    {
        it?.let()
        { uri ->
         //   binding.fragmentStoreSettingStoreImageImageView.load(uri.toString())
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

                //   binding.fragmentFullUserProfileImageViewProfile.load(imageUri.toString())
                // picture taken -> now passing to image cropper
                // launch image cropper activity
                cropCapturedImage.launch(imageUri)
            }
        } // camera activity result closed



    private val  cropCapturedImage = registerForActivityResult(ImageCropHelper.cropImage)
    {
        it?.let()
        { uri ->

            //binding.fragmentStoreSettingStoreImageImageView.load(uri.toString())
            uploadImage(uri)
        } // uri closed
    } // cropCapturedImage closed





    private fun uploadImage(uri: Uri)
    {

        val file = uri.toFile();
        val requestFile = file.asRequestBody(requireContext().contentResolver.getType(uri)?.toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("storeImage", file.path, requestFile)

      //-  storeSettingViewModel.updateStoreImage(body)
    } // uploadImage closed


} // AddPostFragment closed