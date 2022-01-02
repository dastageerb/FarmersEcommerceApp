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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentAddPostBinding
import com.example.farmersecom.features.communitySection.presentation.CommunitySectionViewModel
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
class AddPostFragment : BaseFragment<FragmentAddPostBinding>() , View.OnClickListener
{

    private val viewModel: CommunitySectionViewModel by activityViewModels()
    private lateinit var postImage:Uri;

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentAddPostBinding
    {
        return  FragmentAddPostBinding.inflate(inflater,container,false)
    } // createView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    } // onViewCreated

    private fun initViews()
    {

        binding.fragmentAddPostAddImageTextView.setOnClickListener(this)
       // binding.fragmentAddPostAddImageButton.setOnClickListener(this)
        binding.fragmentAddPostAddPostButton.setOnClickListener(this)
        subscribeToCommunityPostsResponse()
    } // initView closed


    private fun subscribeToCommunityPostsResponse()
    {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.statusMsgResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.fragmentAddPostProgressBar.show()
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d(it.msg.toString())
                            binding.fragmentAddPostProgressBar.hide()
                        }
                        is NetworkResource.Success ->
                        {
                            requireContext().showToast(it.data?.message.toString())
                            binding.fragmentAddPostProgressBar.hide()
                            Timber.tag(Constants.TAG).d(it.data?.toString())
                            findNavController().navigate(R.id.action_addPostFragment_to_communityContributionsFragment)
                        }
                    } // when closed
                } // collector closed
            } // viewLifeCycleOwner.repeatOnLifecycle closed
        } // coroutineScope closed

    } // subscribeToAddCommunityPostResponseResponse


    private fun addPostImage()
    {

            if(requireContext().hasStoragePermission())
            {
                selectImageFromGalleryResult.launch("image/*")
            }else
            {
                multiPermissionCallback.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            } // else closed


    } // AddImage


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
                        permissionDenied(getString(R.string.app_needs_permissions_to_pick_image))
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
            postImage = uri
            binding.fragmentAddPostPostImageImageView.load(uri.toString())
            // binding.fragmentStoreSettingStoreImageImageView.load(uri.toString())
            // pass the uri to  image cropper
            cropGalleryImage.launch(uri)
        }
    } //

    private val cropGalleryImage = registerForActivityResult(ImageCropHelper.cropImage)
    {
        it?.let()
        { uri ->
            postImage = uri
            binding.fragmentAddPostPostImageImageView.load(uri.toString())
        }
    } // pickGalleryImage result closed


    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.fragmentAddPostAddImageTextView ->
            {
                addPostImage()
            }
            R.id.fragmentAddPostAddPostButton ->
            {
                addPost()
            }
        } // when closed
    } // onClick closed

    private fun addPost()
    {
        val postTitle = binding.fragmentAddPostTitleEditText.text.toString().trim();
        val postDesc = binding.fragmentAddPostDescriptionEditText.text.toString().trim();

        if(validateData(postTitle,postDesc) && validateImageView())
        {
            viewModel.addPost(postTitle,postDesc,postImage)
        } // if closed

    } // addPost closed


    private fun validateData(title: String,description: String) :Boolean
    {

        return title.nonEmpty { binding.fragmentAddPostTitleEditText.error = it }
                && description.nonEmpty { binding.fragmentAddPostDescriptionEditText.error = it}
    } // validate Data closed

    private fun validateImageView():Boolean
    {
        if(!this::postImage.isInitialized)
        {
            requireContext().showToast(getString(R.string.select_image))
            return false
        }
        return true
    }


} // AddPostFragment closed