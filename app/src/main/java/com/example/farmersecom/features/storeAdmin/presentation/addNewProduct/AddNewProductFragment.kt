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
import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.features.search.domain.model.categories.Category
import com.example.farmersecom.features.storeAdmin.presentation.StoreProductViewModel
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



    private fun setImageUri(uri:Uri)
    {
        firstImageUri = uri
    }
    private fun getImageURi():Uri
    {
        return firstImageUri;
    }

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

        // these list will come from server
       // binding.autoCompleteAddNewProductProductQuantity.inputType = InputType.TYPE_NULL
       // binding.autoCompleteAddNewProductProductQuantity.setUpAdapter(requireContext(), R.array.Product_Quantity)

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
                viewModel.addNewProductResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Success ->
                        {
                            Timber.tag(Constants.TAG).d("Fragment  ${it.data}")
                        }
                        is NetworkResource.Error ->
                        {
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
                            val categoriesAdapter =
                                CategoriesAdapter(
                                    {
                                        //binding.autoCompleteAddNewProductProductCategory.inputType =
                                        binding.autoCompleteAddNewProductProductCategory.setText(it.name)
                                        categoryId = it.id.toString()
                                    },requireContext(),
                                    R.layout.layout_categories_item,
                                    it.data?.categoryList as MutableList<Category>)
                            binding.autoCompleteAddNewProductProductCategory.setAdapter(categoriesAdapter)
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

         //   val productQuantity = autoCompleteAddNewProductProductQuantity.text.toString().trim()
            val productCategory = autoCompleteAddNewProductProductCategory.text.toString().trim()


            val productPriceInRupees = editTextAddNewProductPriceInRupees.text.toString().trim()

            if( validateData(productName,productDescription, quantityUnit,
                                        productCategory,productPriceInRupees) &&
                validateImageViews() )
            {
                var file = firstImageUri.toFile()

                if(file.exists())
                {
                    requireContext().showToast("Exists"+file.parent)
                }else
                {
                    requireContext().showToast("Do not exists")
                }
                val requestFile = file.asRequestBody(requireContext().contentResolver.getType(getImageURi())?.toMediaTypeOrNull())
                val productImage = MultipartBody.Part.createFormData("productPicture", file.path, requestFile)

                val product = NewProduct(productName
                    ,productPriceInRupees.toInt()
                    ,productDescription
                    ,categoryId
                    ,quantityUnit,
                    viewModel.getUserCity(),1)
                viewModel.addNewProductViewModel(product,productImage)

            } // if closed

        } // apply closed


    } // AddNewProduct closed


    fun createPartFromString(string: String?): RequestBody
    {

        return string!!.toRequestBody(MultipartBody.FORM)




    }


    private fun convertUriToBase64(uri: Uri): String
    {
        val stream = requireContext().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(stream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray,Base64.DEFAULT)
    }



    private fun validateData(name: String,description: String,unit: String,category: String,
                             productPrice: String) :Boolean
    {


        if (!this::categoryId.isInitialized)
        {
            requireContext().showToast("Select Category")
        }
        return name.nonEmpty { binding.editTextAddNewProductName.error = it }
                && description.nonEmpty { binding.editTextAddNewProductDescription.error = it}
                && unit.nonEmpty { requireContext().showToast("Please Select Product Unit") }
                && category.nonEmpty() { binding.autoCompleteAddNewProductProductCategory.error = it }
                && productPrice.nonEmpty() { binding.editTextAddNewProductPriceInRupees.error = it }

    } // validate Data closed


    private fun validateImageViews(): Boolean
    {
        if(!this::firstImageUri.isInitialized)
        {
            requireContext().showToast("Select First Image")
            return false
        }
        return true
//                &&
//        return secondImageUri.toString().nonEmpty() { requireContext().showToast("Select Second Image")}
//                &&
//        return thirdImageUri.toString().nonEmpty() { requireContext().showToast("Select Third Image")}
    }






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
            setImageUri(uri)
            //firstImageUri = uri;
            binding.imageViewAddNewProductFirstImage.setImageURI(getImageURi())
            cropGalleryImage.launch(uri)
        }
    } // selectFirImage closed


    private val cropGalleryImage = registerForActivityResult(ImageCropHelper.cropImage)
    {
        it?.let()
        { uri ->
            binding.imageViewAddNewProductFirstImage.setImageURI(uri)
            firstImageUri = uri
        }
    } // pickGalleryImage result closed



    private val selectSecondImage = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let()
        {
            secondImageUri = uri
            binding.imageViewAddNewProductSecondImage.setImageURI(secondImageUri)

        }
    } // selectSecondImage closed

    private val selectThirdImage = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let()
        {
            thirdImageUri = uri
            binding.imageViewAddNewProductThirdImage.setImageURI(thirdImageUri)
        }
    } // selectFirImage closed



} // AddNewProductFragment