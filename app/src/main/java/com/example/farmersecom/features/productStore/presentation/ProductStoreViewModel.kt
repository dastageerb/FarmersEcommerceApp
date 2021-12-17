package com.example.farmersecom.features.productStore.presentation

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.features.productStore.domain.model.Product
import com.example.farmersecom.features.productStore.domain.usecase.GetStoreByIdUseCase
import com.example.farmersecom.features.productStore.domain.usecase.GetStoreProductsByStoreIdUseCase
import com.example.farmersecom.features.search.domain.model.SearchItem
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductStoreViewModel @Inject constructor (private  val getStoreByIdUseCase: GetStoreByIdUseCase,
                            private val getStoreProductsByStoreIdUseCase: GetStoreProductsByStoreIdUseCase
                            ) : ViewModel()
{


    /** Get Store Response **/
    private val _productStoreResponse: MutableStateFlow<NetworkResource<Product>> = MutableStateFlow(
        NetworkResource.None())
    val productStoreResponse: StateFlow<NetworkResource<Product>> = _productStoreResponse


    fun getStoreDetails(id:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _productStoreResponse.value = NetworkResource.Loading()
        try
        {
            val response = getStoreByIdUseCase.getProductStoreById(id)
          //  _productStoreResponse.value = handleProfileResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _productStoreResponse.value = NetworkResource.Error("Http Exception")
                else -> _productStoreResponse.value =
                    NetworkResource.Error("No Internet Connection: ${e.message}")
            }
        } //
    } // getProfile closed

    private fun handleProfileResponse(response: Response<Product>): NetworkResource<Product>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    }


    /** Get Store Products Response  Response **/

    private var _storeProductsResponse:MutableStateFlow<NetworkResource<PagingData<Product>>> =
        MutableStateFlow(NetworkResource.None());

    val storeProductsResponse = _storeProductsResponse;

    @InternalCoroutinesApi
    fun getStoreProducts(storeId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _storeProductsResponse.value = NetworkResource.Loading()

        val response =  getStoreProductsByStoreIdUseCase.getStoreProductsByStoreId(storeId).cachedIn(viewModelScope)

        response.collect()
        {
            _storeProductsResponse.value = NetworkResource.Success(it)
        }

    } // searchItem closed




} // ProductStoreViewModel closed