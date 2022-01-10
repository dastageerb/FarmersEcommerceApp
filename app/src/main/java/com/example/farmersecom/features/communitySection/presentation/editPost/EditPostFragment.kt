package com.example.farmersecom.features.communitySection.presentation.editPost

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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentEditPostBinding
import com.example.farmersecom.features.communitySection.domain.models.Post
import com.example.farmersecom.features.communitySection.presentation.CommunitySectionViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasStoragePermission
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.imageUtils.ImageCropHelper
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class EditPostFragment : BaseFragment<FragmentEditPostBinding>()
{

    lateinit var postImage:Uri

    lateinit var currentLoadedImage:String
    private val viewModel: CommunitySectionViewModel by activityViewModels()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentEditPostBinding
    {
        return FragmentEditPostBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initViews()

    } // onViewCreated


    private fun initViews()
    {

        viewModel.getPostId.observe(viewLifecycleOwner)
        {
            viewModel.getPostById(it)
            Timber.tag(Constants.TAG).d(""+it)
        } // observer closed
        subscribeToPostByIdResponse()
        subscribeToUpdatePostResponse()


        binding.fragmentEditPostUpdatePostButton.setOnClickListener()
        {
            updatePost()
        }

        binding.fragmentEditPostAddImageTextView.setOnClickListener()
        {
            addPostImage()
        }

    } // initView closed


    private fun updatePost()
    {
        val postTitle = binding.fragmentEditPostTitleEditText.text.toString().trim();
        val postDesc = binding.fragmentEditPostDescriptionEditText.text.toString().trim();

        if(validateData(postTitle,postDesc))
        {
            viewModel.getPostId.observe(viewLifecycleOwner)
            {

                if(this::postImage.isInitialized)
                {
                    Timber.tag(TAG).d("1")
                    viewModel.editORUpdatePost(it,postTitle,postDesc,viewModel.convertImageToMultiPart(postImage))
                }else
                {
                    Timber.tag(TAG).d("2")
                    viewModel.editORUpdatePost(it,title = postTitle,description = postDesc)
                }
            } // observer closed
           // viewModel.(postTitle,postDesc,postImage)
        } // if closed

    } // updatePost closed

    private fun subscribeToUpdatePostResponse()
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
                            binding.fragmentEditPostProgressBar.show()
                          //  binding.fragmentEditProductDetailsLayout.hide()
                        }
                        is NetworkResource.Error ->
                        {

                            Timber.tag(Constants.TAG).d(""+it.msg)
                            binding.fragmentEditPostProgressBar.hide()
                            //binding.fragmentEditProductDetailsLayout.hide()
                        }
                        is NetworkResource.Success ->
                        {
                            it.data?.message?.let { it1 -> requireContext().showToast(it1) }
                            findNavController().navigate(R.id.action_editPostFragment_to_communityContributionsFragment)
                            Timber.tag(Constants.TAG).d(""+it.data)
                           // updateViews(it.data)
                        }
                    } // when closed
                } // collector closed
            } // viewLifeCycleOwner.repeatOnLifecycle closed
        } // coroutineScope closed

    } // subscribeToUpdatePostResponse




    private fun validateData(title: String,description: String) :Boolean
    {

        return title.nonEmpty { binding.fragmentEditPostTitleEditText.error = it }
                && description.nonEmpty { binding.fragmentEditPostDescriptionEditText.error = it}
    } // validate Data closed






    private fun subscribeToPostByIdResponse()
    {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.getPostByIdResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.fragmentEditPostProgressBar.show()
                            binding.fragmentEditProductDetailsLayout.hide()
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(Constants.TAG).d(""+it.msg)
                            binding.fragmentEditPostProgressBar.hide()
                            binding.fragmentEditProductDetailsLayout.hide()
                        }
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d(""+it.data)
                            updateViews(it.data)
                        }
                    } // when closed
                } // collector closed
            } // viewLifeCycleOwner.repeatOnLifecycle closed
        } // coroutineScope closed

    } // subscribeToCommunityPostDetailsResponse


    private fun updateViews(post: Post?)
    {
        currentLoadedImage = post?.image.toString()
        Picasso.get().load(currentLoadedImage)
            .into(binding.fragmentEditPostPostImageImageView, object : Callback
            {
                override fun onSuccess()
                {
                    Timber.tag(Constants.TAG).d("Success")

                    try
                    {
                        binding.fragmentEditPostProgressBar.hide()
                    }catch (e:java.lang.Exception)
                    {

                    }


                }
                override fun onError(e: Exception?)
                {

                    try
                    {
                        binding.fragmentEditPostProgressBar.hide()
                    }catch (e:java.lang.Exception)
                    {

                    }

                }
            })

        binding.fragmentEditPostTitleEditText.setText(post?.title)
        binding.fragmentEditPostDescriptionEditText.setText(post?.description)
        binding.fragmentEditProductDetailsLayout.show()

    } // updateView closed




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

            Picasso.get().load(uri.toString())
                .into(binding.fragmentEditPostPostImageImageView, object : Callback
                {
                    override fun onSuccess()
                    {
                        Timber.tag(Constants.TAG).d("Success")
                        binding.fragmentEditPostProgressBar.hide()
                    }
                    override fun onError(e: Exception?)
                    {
                        Timber.tag(Constants.TAG).d(""+e?.message)
                        binding.fragmentEditPostProgressBar.hide()
                    }
                })

            cropGalleryImage.launch(uri)
        }
    } //

    private val cropGalleryImage = registerForActivityResult(ImageCropHelper.cropImage)
    {
        it?.let()
        { uri ->
            postImage = uri
            currentLoadedImage = uri.toString()

            binding.fragmentEditPostPostImageImageView.load(postImage.toString(),R.drawable.image_place_holder)
            //binding.fragmentEditPostPostImageImageView.setImageURI(c)
//            Picasso.get().load(currentLoadedImage)
//                .into(binding.fragmentEditPostPostImageImageView, object : Callback
//                {
//                    override fun onSuccess()
//                    {
//                        Timber.tag(Constants.TAG).d("check Success")
//                        binding.fragmentEditPostProgressBar.hide()
//                    }
//                    override fun onError(e: Exception?)
//                    {
//                        Timber.tag(Constants.TAG).d(" check "+e?.message)
//                        binding.fragmentEditPostProgressBar.hide()
//                    }
//                })
        }
    } // pickGalleryImage result closed













} // EditPostFragment