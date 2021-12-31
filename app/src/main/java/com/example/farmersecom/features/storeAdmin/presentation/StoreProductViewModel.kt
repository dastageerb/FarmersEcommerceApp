package com.example.farmersecom.features.storeAdmin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.search.domain.model.categories.CategoriesResponse
import com.example.farmersecom.features.storeAdmin.domain.useCases.AddProductUseCase
import com.example.farmersecom.features.storeAdmin.domain.useCases.ChangeProductStatusUseCase
import com.example.farmersecom.features.storeAdmin.domain.useCases.DeleteProductById
import com.example.farmersecom.features.search.domain.useCases.GetAllCategories
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StoreProductViewModel @Inject constructor(
    private  val addProductUseCaseCase: AddProductUseCase,
    private val getAllCategories: GetAllCategories,
    private val sharedPrefsHelper: SharedPrefsHelper
    ) : ViewModel()
{


    /**  Get  Product Changes Msg and Response Response
     * same used for changing product status and deleting responses
     * **/


    private var _statusMsgResponse: MutableSharedFlow<NetworkResource<StatusMsgResponse>>
            = MutableSharedFlow(replay = 0)
    val statusMsgResponse: SharedFlow<NetworkResource<StatusMsgResponse>>
            = _statusMsgResponse












    fun addNewProductViewModel(newProduct: NewProduct
                               ,firstFile: MultipartBody.Part
                               ,secondFile: MultipartBody.Part
                               ,thirdFile: MultipartBody.Part) = viewModelScope.launch(Dispatchers.IO)
    {

        _statusMsgResponse.emit(NetworkResource.Loading())
        try
        {
            val response = addProductUseCaseCase.addNewProduct(newProduct,firstFile,secondFile,thirdFile)
            _statusMsgResponse.emit(handleStatusMessageResponse(response))

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
        } // catch closed
    } // addNewProductClosed




    private fun handleStatusMessageResponse(response: Response<StatusMsgResponse>): NetworkResource<StatusMsgResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    } // handleStatusMessageResponse










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