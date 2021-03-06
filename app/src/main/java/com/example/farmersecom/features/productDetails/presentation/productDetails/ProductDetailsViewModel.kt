package com.example.farmersecom.features.productDetails.presentation.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.User
import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.features.cart.domain.usecase.CheckIfProductExistsInCartUseCase
import com.example.farmersecom.features.cart.domain.usecase.DeleteAllCartItemsUseCase
import com.example.farmersecom.features.cart.domain.usecase.GetCartItemsUseCase
import com.example.farmersecom.features.productDetails.domain.model.NavigationEntity
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.features.productDetails.domain.useCase.GetProductByIdUseCase
import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.features.profile.domain.model.Profile
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.extensionFunctions.handleErros.HandleErrorExtension.handleException
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val sharedPrefsHelper:SharedPrefsHelper,
    private val getAllCartItemsUseCase: GetCartItemsUseCase,
    private val checkIfProductExistsInCartUseCase: CheckIfProductExistsInCartUseCase,

    )
    :ViewModel()
{



    val getAllCartItems: Flow<List<CartItem>> = getAllCartItemsUseCase.getCartItems()

    val exists = MutableSharedFlow<Boolean>(0)

//
    fun exists(productId:String)  = viewModelScope.launch(Dispatchers.IO)
    {
        exists.emit(checkIfProductExistsInCartUseCase.exists(productId))
    }




            // for shared Use
            private val setProductId :MutableStateFlow<String> = MutableStateFlow("")
            val getProductId :StateFlow<String> = setProductId
            fun setProductId(query:String)
            {
                setProductId.value = query
            } //



            private val _productDetailsResponse: MutableStateFlow<NetworkResource<ProductDetailsResponse>> = MutableStateFlow(
                NetworkResource.None())
            val productDetailsResponse: StateFlow<NetworkResource<ProductDetailsResponse>> = _productDetailsResponse


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



            fun getAuthToken() : String? = sharedPrefsHelper.getToken()

    fun getUser() : User? = sharedPrefsHelper.getUser()




}