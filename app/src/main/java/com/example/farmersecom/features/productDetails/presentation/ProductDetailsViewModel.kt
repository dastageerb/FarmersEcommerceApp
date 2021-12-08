package com.example.farmersecom.features.productDetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.features.productDetails.domain.useCase.GetProductByIdUseCase
import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.features.profile.domain.model.Profile
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.extensionFunctions.handleErros.HandleErrorExtension.handleException
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val getProductByIdUseCase: GetProductByIdUseCase) :ViewModel()
{


    private val _productDetailsResponse: MutableStateFlow<NetworkResource<JsonObject>> = MutableStateFlow(
        NetworkResource.None())
    val productDetailsResponse: StateFlow<NetworkResource<JsonObject>> = _productDetailsResponse


    fun getProductDetails(id:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _productDetailsResponse.value = NetworkResource.Loading()
        try
        {
            val response = getProductByIdUseCase.getProductById(id)
            _productDetailsResponse.value = handleProfileResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _productDetailsResponse.value = NetworkResource.Error("Http Exception")
                else -> _productDetailsResponse.value =
                    NetworkResource.Error("No Internet Connection: ${e.message}")
            }
        } //
    } // getProfile closed

    private fun handleProfileResponse(response: Response<JsonObject>): NetworkResource<JsonObject>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    }


}