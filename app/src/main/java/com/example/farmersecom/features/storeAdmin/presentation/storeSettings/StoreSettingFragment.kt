package com.example.farmersecom.features.storeAdmin.presentation.storeSettings

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.CompoundButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.farmersecom.BuildConfig
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentStoreSettingBinding
import com.example.farmersecom.features.productStore.domain.model.storeDetails.StoreDetailsResponse
import com.example.farmersecom.features.profile.data.framework.entities.SetupStoreData
import com.example.farmersecom.features.storeAdmin.domain.model.updateStore.UpdateStore
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasCameraPermission
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasStoragePermission
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.imageUtils.ImageCropHelper
import com.example.farmersecom.utils.imageUtils.ImageUtils
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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


        binding.fragmentStoreSettingsDeliveryInfoSwitch.setOnCheckedChangeListener(this)
        binding.fragmentStoreSettingEditStoreButton.setOnClickListener(this)
        binding.fragmentStoreSettingsStoreUpdateButton.setOnClickListener(this)
        binding.fragmentStoreSettingsChangeStoreImageButton.setOnClickListener(this)

    } // onView created closed


    private fun subscribeToStatusResponses()
    {

        // when store details updated
        // when store delivery method updated
        // when store image changed
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


//    private fun subscribeToImageChangedResponse()
//    {
//        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
//        {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
//            {
//                storeSettingViewModel.storeImageChangedResponse.collect()
//                {
//                    when(it)
//                    {
//                        is NetworkResource.Loading ->
//                        {
//                            // binding.orderDetailsForSellerFragmentProgressBar.show()
//                        }
//                        is NetworkResource.Success ->
//                        {
//                            //  binding.orderDetailsForSellerFragmentProgressBar.hide()
//                            // Timber.tag(Constants.TAG).d("${it.data?.messege}")
//                            //updateViews(it.data)
//                        }
//                        is NetworkResource.Error ->
//                        {
//                            Timber.tag(Constants.TAG).d("${it.msg}")
//                        }
//                    }// when closed
//                } // getProfile closed
//            } // repeatOnLife cycle closed
//        } /// lifecycleScope closed
//    } // subscriber closed



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
            R.id.fragmentStoreSettingsChangeStoreImageButton ->
            {
                changeStoreImage()
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


    // onCheckedListener on switch
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


    /** Change Store Image ****/

    private fun changeStoreImage()
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
            binding.fragmentStoreSettingStoreImageImageView.load(uri.toString())
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

            binding.fragmentStoreSettingStoreImageImageView.load(uri.toString())
            uploadImage(uri)
        } // uri closed
    } // cropCapturedImage closed





    private fun uploadImage(uri: Uri)
    {

        val file = uri.toFile();
        val requestFile = file.asRequestBody(requireContext().contentResolver.getType(uri)?.toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("storeImage", file.path, requestFile)

        storeSettingViewModel.updateStoreImage(body)
    } // uploadImage closed





} // StoreSettingFragment closed