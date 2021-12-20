package com.example.farmersecom.features.productStore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.features.productStore.domain.model.storeDetails.StoreDetailsResponse
import com.example.farmersecom.features.productStore.domain.usecase.GetStoreByIdUseCase
import com.example.farmersecom.features.productStore.domain.usecase.GetStoreProductsByStoreIdUseCase
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.features.productStore.domain.model.storeProducts.Product
import com.example.farmersecom.features.productStore.domain.model.storeProducts.StoreProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductStoreViewModel @Inject constructor (private  val getStoreByIdUseCase: GetStoreByIdUseCase,
                            private val getStoreProductsByStoreIdUseCase: GetStoreProductsByStoreIdUseCase
                            ) : ViewModel()
{


    /** Get com.example.farmersecom.features.productDetails.domain.model.Store Response **/
    private val _productStoreResponse: MutableStateFlow<NetworkResource<StoreDetailsResponse>> = MutableStateFlow(
        NetworkResource.None())
    val productStoreResponse: StateFlow<NetworkResource<StoreDetailsResponse>> = _productStoreResponse


    fun getStoreDetails(id:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _productStoreResponse.value = NetworkResource.Loading()
        try
        {
            val response = getStoreByIdUseCase.getProductStoreById(id)
            _productStoreResponse.value = handleStoreDetailsResponse(response)
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

    private fun handleStoreDetailsResponse(response: Response<StoreDetailsResponse>): NetworkResource<StoreDetailsResponse>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    }


    /** Get .Store Products Response  Response **/

    private var _storeProductsResponse:MutableStateFlow<NetworkResource<List<Product>>> =
        MutableStateFlow(NetworkResource.None());

    val storeProductsResponse:StateFlow<NetworkResource<List<Product>>> = _storeProductsResponse;


    fun getStoreProducts(storeId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _storeProductsResponse.value = NetworkResource.Loading()

        try
        {
            val response = getStoreProductsByStoreIdUseCase.getStoreProductsByStoreId(storeId)
            _storeProductsResponse.value = handleStoreProductsResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _productStoreResponse.value = NetworkResource.Error("Http Exception")
                else -> _productStoreResponse.value =
                    NetworkResource.Error("No Internet Connection: ${e.message}")
            }
        } //
    } // searchItem closed

    private fun handleStoreProductsResponse(response: Response<StoreProducts>): NetworkResource<List<Product>>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body()?.products)
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    }

    /// Getters Setters
    val setStoreId :MutableStateFlow<String> = MutableStateFlow("")
    val getStoreId :StateFlow<String> = setStoreId
    fun setStoreId(id:String)
    {
        setStoreId.value = id
    } //



} // ProductStoreViewModel closed