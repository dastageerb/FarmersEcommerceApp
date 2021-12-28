package com.example.farmersecom.features.storeAdmin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.categories.CategoriesResponse
import com.example.farmersecom.features.storeAdmin.domain.useCases.AddProductUseCase
import com.example.farmersecom.features.storeAdmin.domain.useCases.ChangeProductStatusUseCase
import com.example.farmersecom.features.storeAdmin.domain.useCases.DeleteProductById
import com.example.farmersecom.features.storeAdmin.domain.useCases.GetAllCategories
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StoreProductViewModel @Inject constructor(
    private  val addProductUseCaseCase: AddProductUseCase,
    private val deleteProductById: DeleteProductById,
    private val changeProductStatusUseCase: ChangeProductStatusUseCase,
    private val getAllCategories: GetAllCategories,
    private val sharedPrefsHelper: SharedPrefsHelper
    ) : ViewModel()
{


    /**  Get  Product Changes Msg and Response Response
     * same used for changing product status and deleting responses
     * **/

    private var _statusMsgResponse:MutableStateFlow<NetworkResource<StatusMsgResponse>>
            = MutableStateFlow(NetworkResource.None())
    val statusMsgResponse: StateFlow<NetworkResource<StatusMsgResponse>>
            = _statusMsgResponse


    fun changeProductStatus(status:Boolean,productId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val response = changeProductStatusUseCase.changeProductStatus(status,productId)
            _statusMsgResponse.value = handleStatusMessageResponse(response)

        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException ->  _statusMsgResponse.value = NetworkResource.Error("Something went wrong")
                else ->
                {
                    _statusMsgResponse.value = NetworkResource.Error(""+e.message)
                }
            } // when closed
        }
    } //  changeProductStatus closed

    fun deleteProductbyId(productId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val response = deleteProductById.deleteProductById(productId)
            _statusMsgResponse.value = handleStatusMessageResponse(response)

        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException ->  _statusMsgResponse.value = NetworkResource.Error("Something went wrong")
                else ->
                {
                    _statusMsgResponse.value = NetworkResource.Error(""+e.message)
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












//// This will be used for adding and updating a product


    private var _addNewProductResponse:MutableStateFlow<NetworkResource<NewProductResponse>>
        = MutableStateFlow(NetworkResource.None())
    val addNewProductResponse: StateFlow<NetworkResource<NewProductResponse>>
            = _addNewProductResponse


    fun addNewProductViewModel(newProduct: NewProduct, file: MultipartBody.Part) = viewModelScope.launch(Dispatchers.IO)
    {
    //    _addNewProductResponse.value = NetworkResource.Loading()
          try
            {
                val response = addProductUseCaseCase.addNewProduct(newProduct,file);

                 _addNewProductResponse.value = handleResponse(response)

            }catch (e:Exception)
            {
                when (e)
                {
                    is HttpException -> _addNewProductResponse.value = NetworkResource.Error("Something went wrong")
                    else ->
                    {
                        _addNewProductResponse.value = NetworkResource.Error("No Internet Connection : "+e.message)
                        _addNewProductResponse.value = NetworkResource.Error("exception : $e")
                        _addNewProductResponse.value = NetworkResource.Error("exception : ${e.stackTrace}")
                        _addNewProductResponse.value = NetworkResource.Error("exception : ${e.cause}")
                    }
                } // when closed
            }
    }

    private fun handleResponse(response: Response<NewProductResponse>): NetworkResource<NewProductResponse>
    {
                return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }












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
    }




    /// Get User for adding city of product

    fun getUserCity() = sharedPrefsHelper.getUser()?.city!!


} // RegisterViewModel closed