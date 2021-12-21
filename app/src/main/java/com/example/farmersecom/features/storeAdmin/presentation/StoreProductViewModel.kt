package com.example.farmersecom.features.storeAdmin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.features.storeAdmin.data.framework.StoreAdminApi
import com.example.farmersecom.features.storeAdmin.data.framework.entities.NewProduct
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.features.storeAdmin.data.framework.entities.responses.NewProductResponse
import com.example.farmersecom.features.storeAdmin.domain.model.categories.CategoriesResponse
import com.example.farmersecom.features.storeAdmin.domain.useCases.AddProductUseCase
import com.example.farmersecom.features.storeAdmin.domain.useCases.GetAllCategories
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StoreProductViewModel @Inject constructor(
    private  val addProductUseCaseCase: AddProductUseCase,
    private val getAllCategories: GetAllCategories,
    private val storeAdminApi: StoreAdminApi
                                                ) : ViewModel()
{













//// This will be used for adding and updating a product


    private var _addNewProductResponse:MutableStateFlow<NetworkResource<NewProductResponse>>
        = MutableStateFlow(NetworkResource.None())
    val addNewProductResponse: StateFlow<NetworkResource<NewProductResponse>>
            = _addNewProductResponse


    fun addNewProductViewModel(newProduct: NewProduct,file: MultipartBody.Part) = viewModelScope.launch(Dispatchers.IO)
    {
    //    _addNewProductResponse.value = NetworkResource.Loading()
          try
            {
                val response = addProductUseCaseCase.addNewProduct(newProduct,file);
              //  Timber.tag(TAG).d("viewmodel :"+response.body())
             //   Timber.tag(TAG).d("viewmodel "+response.code())
               // Timber.tag(TAG).d("viewmodel "+response.errorBody()?.getMessage())
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

//    private fun handleResponse(response: Response<NewProductResponse>):NetworkResource<NewProductResponse>
//    {
//        return when(response.code())
//        {
//            200,201 -> NetworkResource.Success(response.body())
//            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
//            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
//        } // when closed
//
//    } // when closed

//
//    private fun handleResponse(response: Response<RegisterResponse>): NetworkResource<RegisterResponse>
//    {
//        return when(response.code())
//        {
//            200,201 -> NetworkResource.Success(response.body())
//            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
//            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
//        } // when closed
//    } // handle Response closed


    fun addProduct() = viewModelScope.launch(Dispatchers.IO)
    {

        try
        {
            val response = storeAdminApi.uploadProduct(
            "phataka",35,1,"description","kg",
                "61b061c1f4ba7a31c043253d","mps",)
            Timber.tag(TAG).d("response : "+response.body())
            Timber.tag(TAG).d("response : "+response.isSuccessful)
            Timber.tag(TAG).d("response : "+response.code())
            if(response.code()!=200)
            {
                Timber.tag(TAG).d(""+response.errorBody()?.getMessage())
            }
    }catch (e:java.lang.Exception)
        {
            Timber.tag(TAG).d(""+e.message)
        }

    } //








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








} // RegisterViewModel closed