package com.example.farmersecom.features.storeAdmin.presentation.editProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.features.productDetails.domain.useCase.GetProductByIdUseCase
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.search.domain.model.categories.CategoriesResponse
import com.example.farmersecom.features.storeAdmin.domain.model.editProduct.EditProduct
import com.example.farmersecom.features.storeAdmin.domain.useCases.EditProductUseCase
import com.example.farmersecom.features.search.domain.useCases.GetAllCategories
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(private val getAllCategories: GetAllCategories,
                                               private val editProductUseCase: EditProductUseCase,
                                               private val productDetailsByIdUseCase: GetProductByIdUseCase) : ViewModel()
{



    val setProductId :MutableStateFlow<String> = MutableStateFlow("")
    val getProductId :StateFlow<String> = setProductId
    fun setProductId(query:String)
    {
        setProductId.value = query
    } //


    /** Get all categories response  ***/

    private var _categoriesResponse:MutableStateFlow<NetworkResource<CategoriesResponse>>
            = MutableStateFlow(NetworkResource.None())
    val categoriesResponse: StateFlow<NetworkResource<CategoriesResponse>>
            = _categoriesResponse


    fun getAllCategories() = viewModelScope.launch(Dispatchers.IO)
    {
        _categoriesResponse.value = NetworkResource.Loading()
        try
        {
            val response = getAllCategories.getAllCategories()
            _categoriesResponse.value = handleCategoriesResponse(response)

        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException ->  _categoriesResponse.value = NetworkResource.Error("Something went wrong")
                else ->
                {
                    _categoriesResponse.value = NetworkResource.Error(""+e.message)
                }
            } // when closed
        }
    }

    private fun handleCategoriesResponse(response: Response<CategoriesResponse>): NetworkResource<CategoriesResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    } // handleCategoriesResponse closed

    private val _productDetailsResponse: MutableStateFlow<NetworkResource<ProductDetailsResponse>> = MutableStateFlow(
        NetworkResource.None())
    val productDetailsResponse: StateFlow<NetworkResource<ProductDetailsResponse>> = _productDetailsResponse



    fun getProductDetails(id:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _productDetailsResponse.value = NetworkResource.Loading()
        try
        {
            val response = productDetailsByIdUseCase.getProductById(id)
            _productDetailsResponse.value = handleProfileResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _productDetailsResponse.value = NetworkResource.Error("Http Exception")
                else -> _productDetailsResponse.value =
                    NetworkResource.Error("No Internet Connection: ${e.message}")
            }
        }


    } // getProfile closed

    private fun handleProfileResponse(response: Response<ProductDetailsResponse>): NetworkResource<ProductDetailsResponse>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    } //










    // editProduct
    private var _statusMsgResponse:MutableSharedFlow<NetworkResource<StatusMsgResponse>>
            = MutableSharedFlow(replay = 0)
    val statusMsgResponse: SharedFlow<NetworkResource<StatusMsgResponse>>
            = _statusMsgResponse


    fun editProduct(editProduct: EditProduct,productId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.emit(NetworkResource.Loading())
     //   _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val response = editProductUseCase.editProduct(editProduct,productId)
            _statusMsgResponse.emit(handleStatusMessageResponse(response))
        //     _statusMsgResponse.value = handleStatusMessageResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException ->{  _statusMsgResponse.emit(NetworkResource.Error(e.message)) }
                else ->
                {
                    _statusMsgResponse.emit(NetworkResource.Error(e.message))
                }
            } // when closed
        }
    } //  changeProductStatus closed



    private fun handleStatusMessageResponse(response: Response<StatusMsgResponse>): NetworkResource<StatusMsgResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }







} // RegisterViewModel closed