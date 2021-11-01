package com.example.farmersecom.features.store.presentation.addNewProduct

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentAddNewProductBinding
import com.example.farmersecom.features.authentication.presentation.register.utils.Utils.setUpAdapter
import com.example.farmersecom.features.store.domain.model.NewProduct
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.permission.Permissions.hasStoragePermission
import com.google.android.material.button.MaterialButton
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.*


class AddNewProductFragment : BaseFragment<FragmentAddNewProductBinding>() , View.OnClickListener
{

    private lateinit var firstImageUri: Uri
    private lateinit var secondImageUri: Uri
    private lateinit var thirdImageUri: Uri

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentAddNewProductBinding
    {
        return FragmentAddNewProductBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initView();


    } // onViewCreated closed

    private fun initView()
    {

        binding.imageViewAddNewProductFirstImage.setOnClickListener(this)
        binding.imageViewAddNewProductSecondImage.setOnClickListener(this)
        binding.imageViewAddNewProductThirdImage.setOnClickListener(this)
        binding.buttonAddProductFragSubmit.setOnClickListener(this)

        binding.autoCompleteAddNewProductProductQuantity.inputType = InputType.TYPE_NULL
        binding.autoCompleteAddNewProductProductQuantity.setUpAdapter(requireContext(), R.array.Product_Quantity)

    } // initView closed

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

            val productQuantity = autoCompleteAddNewProductProductQuantity.text.toString().trim()

            val productPriceInRupees = editTextAddNewProductPriceInRupees.text.toString().trim()



            if( validateData(productName,productDescription, quantityUnit,
                                        productQuantity,productPriceInRupees) &&
                validateImageViews(firstImageUri,secondImageUri,thirdImageUri) )
            {

               val newProduct = NewProduct(productName
                   ,productDescription, quantityUnit
                   ,productQuantity,productPriceInRupees,
                    listOf<String>(convertUriToBase64(firstImageUri)
                        ,convertUriToBase64(secondImageUri) ,convertUriToBase64(thirdImageUri)))
                requireContext().showToast(convertUriToBase64(firstImageUri))

            } // if closed

        } // apply closed


    } // AddNewProduct closed

    private fun convertUriToBase64(uri: Uri): String
    {
        val stream = requireContext().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(stream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray,Base64.DEFAULT)
    }


    private fun validateData(name: String,description: String,unit: String,quantity: String,
                             productPrice: String) :Boolean
    {

        return name.nonEmpty { binding.editTextAddNewProductName.error = it }
                && description.nonEmpty { binding.editTextAddNewProductDescription.error = it}
                && unit.nonEmpty { requireContext().showToast("Please Select Product Unit") }
                && quantity.nonEmpty() { binding.autoCompleteAddNewProductProductQuantity.error = it }
                && productPrice.nonEmpty() { binding.editTextAddNewProductPriceInRupees.error = it }

    } // validate Data closed


    private fun validateImageViews(firstImageUri: Uri, secondImageUri: Uri, thirdImageUri: Uri): Boolean
    {
        return firstImageUri.toString().nonEmpty() { requireContext().showToast("Select First Image")}
                &&
        return secondImageUri.toString().nonEmpty() { requireContext().showToast("Select Second Image")}
                &&
        return thirdImageUri.toString().nonEmpty() { requireContext().showToast("Select Third Image")}
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
            firstImageUri = uri
            binding.imageViewAddNewProductFirstImage.setImageURI(firstImageUri)
        }
    } // selectFirImage closed

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