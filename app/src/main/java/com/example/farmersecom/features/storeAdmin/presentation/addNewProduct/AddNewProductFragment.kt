package com.example.farmersecom.features.storeAdmin.presentation.addNewProduct

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentAddNewProductBinding
import com.example.farmersecom.features.authentication.presentation.register.utils.Utils.setUpAdapter
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasStoragePermission
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.android.material.button.MaterialButton
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import android.util.Base64
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.features.search.domain.model.categories.CategoriesResponse
import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.features.search.domain.model.categories.Category
import com.example.farmersecom.features.storeAdmin.presentation.StoreProductViewModel
import com.example.farmersecom.utils.extensionFunctions.picasso.PicassoExtensions.load
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.imageUtils.ImageCropHelper
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


@AndroidEntryPoint
class AddNewProductFragment : BaseFragment<FragmentAddNewProductBinding>() , View.OnClickListener
{

    private lateinit var firstImageUri: Uri
    private lateinit var secondImageUri: Uri
    private lateinit var thirdImageUri: Uri

     lateinit var categoryId:String

    private val viewModel: StoreProductViewModel by viewModels()




    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentAddNewProductBinding
    {
        return FragmentAddNewProductBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initView();

        viewModel.getAllCategories()
        subscribeToGetALLCategoriesFlow()
        subscribeAddNetProductResponseFlow()

    } // onViewCreated closed

    private fun initView()
    {

        binding.imageViewAddNewProductFirstImage.setOnClickListener(this)
        binding.imageViewAddNewProductSecondImage.setOnClickListener(this)
        binding.imageViewAddNewProductThirdImage.setOnClickListener(this)
        binding.buttonAddProductFragSubmit.setOnClickListener(this)


         binding.autoCompleteAddNewProductProductCategory.inputType = InputType.TYPE_NULL
        binding.autoCompleteAddNewProductProductCategory.inputType = InputType.TYPE_NULL
        binding.autoCompleteAddNewProductProductCategory.setUpAdapter(requireContext(), R.array.Product_Category)

    } // initView closed


    private fun subscribeAddNetProductResponseFlow()
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
                            binding.fragmentAddNewProductProgressBar.show()
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Success ->
                        {
                            binding.fragmentAddNewProductProgressBar.hide()
                            it.data?.message?.let { it1 -> requireContext().showToast(it1) }
                            findNavController().navigate(R.id.action_addNewProductFragment_to_storeFragment)
                        }
                        is NetworkResource.Error ->
                        {
                            binding.fragmentAddNewProductProgressBar.hide()
                            Timber.tag(Constants.TAG).d(" Fragment  ${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed

    } // subscribeProfileResponseFlow closed


    // check Permission onEach Image Clicked

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.imageViewAddNewProductFirstImage ->
            {
                if(requireContext().hasStoragePermission())
                {
                    selectFirstImage.launch("image/*")
                }else
                {
                    multiPermissionCallback.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                } // else closed

            }  // pickFirstImageClosed
            R.id.imageViewAddNewProductSecondImage ->
            {
                if(requireContext().hasStoragePermission())
                {
                    selectSecondImage.launch("image/*")
                }else
                {
                multiPermissionCallback.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                }// else closed
            } // pickSecondImage closed
            R.id.imageViewAddNewProductThirdImage ->
            {
                if(requireContext().hasStoragePermission())
                {
                    selectThirdImage.launch("image/*")
                }else
                {
                    multiPermissionCallback.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                } // else closed
            } // ThirdImage closed
            R.id.buttonAddProductFragSubmit ->  addNewProduct();
        } // when closed
    } // onClick



    private fun subscribeToGetALLCategoriesFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.categoriesResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            requireContext().showToast("Loading Categories")
                            binding.fragmentAddNewProductProgressBar.show()
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Success ->
                        {
                            binding.fragmentAddNewProductProgressBar.hide()
                            setupCategoriesAdapter(it.data)
                            Timber.tag(Constants.TAG).d("Fragment  ${it.data}")
                        }
                        is NetworkResource.Error ->
                        {
                            binding.fragmentAddNewProductProgressBar.hide()
                            Timber.tag(Constants.TAG).d(" Fragment  ${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed

    } // subscribeProfileResponseFlow closed



    private fun setupCategoriesAdapter(it: CategoriesResponse?)
    {

        val categoriesAdapter =
            CategoriesAdapter(
                {
                    // onClick
                    binding.autoCompleteAddNewProductProductCategory.setText(it.name)

                    if(it.name.equals("cattle",true))
                    {
                        binding.buttonProductQuantityUnitGroup.check(R.id.buttonProductQuantityUnitCattle)
                    }

                    categoryId = it.id.toString()
                },requireContext(),
                R.layout.layout_categories_item,
                it?.categoryList as MutableList<Category>)
        binding.autoCompleteAddNewProductProductCategory.setAdapter(categoriesAdapter)

    }




    private fun addNewProduct()
    {

        binding.apply()
        {

            val productName = editTextAddNewProductName.text.toString().trim();
            val productDescription = editTextAddNewProductDescription.text.toString().trim();

            /// Get Selected Quantity Button Id first
            val quantityButtonId   = buttonProductQuantityUnitGroup.checkedButtonId
            // then get Quantity based on selected button
            val quantityUnit = buttonProductQuantityUnitGroup
                .findViewById<MaterialButton>(quantityButtonId).text.toString()

           val productCategory = autoCompleteAddNewProductProductCategory.text.toString().trim()


            val productPriceInRupees = editTextAddNewProductPriceInRupees.text.toString().trim()

            if( validateData(productName,productDescription, quantityUnit,
                                        productCategory,productPriceInRupees) &&
                validateImageViews())
            {
                val product = NewProduct(productName
                    ,productPriceInRupees.toInt()
                    ,productDescription
                    ,categoryId
                    ,quantityUnit,
                    viewModel.getUserCity(),1)
                viewModel.addNewProductViewModel(product
                    ,convertImageToMultiPart(firstImageUri)
                ,convertImageToMultiPart(secondImageUri)
                ,convertImageToMultiPart(thirdImageUri))

            } // if closed

        } // apply closed


    } // AddNewProduct closed


    private fun convertImageToMultiPart(imageUri:Uri):MultipartBody.Part
    {
        val file = imageUri.toFile()
        val requestFile = file.asRequestBody(requireContext().contentResolver.getType(imageUri)?.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("productPicture", file.path, requestFile)
    }

    private fun convertUriToBase64(uri: Uri): String
    {
        val stream = requireContext().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(stream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray,Base64.DEFAULT)
    } // convertUriToBase64



    private fun validateData(name: String,description: String,unit: String,category: String,
                             productPrice: String) :Boolean
    {

        return name.nonEmpty { binding.editTextAddNewProductName.error = it }
                && description.nonEmpty { binding.editTextAddNewProductDescription.error = it}
                && unit.nonEmpty { requireContext().showToast(getString(R.string.plz_select_product_unit)) }
                && category.nonEmpty() { binding.autoCompleteAddNewProductProductCategory.error = it }
                && productPrice.nonEmpty() { binding.editTextAddNewProductPriceInRupees.error = it }

    } // validate Data closed


    private fun validateImageViews(): Boolean
    {
        return if(!this::firstImageUri.isInitialized)
        {
            requireContext().showToast(getString(R.string.select_first_image))
            false
        } else if(!this::secondImageUri.isInitialized)
        {
            requireContext().showToast(getString(R.string.select_second_image))
            false
        } else if(!this::thirdImageUri.isInitialized)
        {
            requireContext().showToast(getString(R.string.select_third_image))
            false
        } // else closed
        else
        {
            true
        }

    } // validateImageViews






    // convert the Image To Base64




    /** Handle Permissions **/

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
                      //  getImageFromGallery()
                    }else
                    {
                        permissionDenied("App Needs Permission to Pick Image")
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






    /***Select Images    **/






    private val selectFirstImage = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let()
        {

            firstImageUri = uri;
            binding.imageViewAddNewProductFirstImage.load(firstImageUri.toString())
                firstImageCropper.launch(uri)
        }
    } // selectFirImage closed


    private val firstImageCropper = registerForActivityResult(ImageCropHelper.cropImage)
    {
        it?.let()
        { uri ->
            firstImageUri = uri
            binding.imageViewAddNewProductFirstImage.load(firstImageUri.toString())
        }
    } // firstImageCropper  closed



    private val selectSecondImage = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let()
        {
            secondImageUri = uri;
            binding.imageViewAddNewProductSecondImage.load(secondImageUri.toString())
            secondImageCropper.launch(uri)

        }
    } // selectSecondImage closed



    private val secondImageCropper = registerForActivityResult(ImageCropHelper.cropImage)
    {
        it?.let()
        { uri ->
            secondImageUri = uri
            binding.imageViewAddNewProductSecondImage.load(secondImageUri.toString())
        }
    } // secondImageCropper closed closed





    private val selectThirdImage = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let()
        {

            thirdImageUri = uri;
            binding.imageViewAddNewProductThirdImage.load(thirdImageUri.toString())
            thirdImageCropper.launch(uri)
        }
    } // selectThirdImage closed



    private val thirdImageCropper = registerForActivityResult(ImageCropHelper.cropImage)
    {
        it?.let()
        { uri ->
            thirdImageUri = uri
            binding.imageViewAddNewProductThirdImage.load(thirdImageUri.toString())
        }
    } // secondImageCropper closed closed




} // AddNewProductFragment