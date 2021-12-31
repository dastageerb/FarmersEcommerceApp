package com.example.farmersecom.features.buyNow.presentation.orderDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.RegisterResponse
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.User
import com.example.farmersecom.features.buyNow.domain.model.request.OrderRequest
import com.example.farmersecom.features.buyNow.domain.model.response.Order
import com.example.farmersecom.features.buyNow.domain.model.response.OrderResponse
import com.example.farmersecom.features.buyNow.domain.usecase.PlaceOrderUseCase
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(
   private val placeOrderUseCase: PlaceOrderUseCase,
   private val sharedPrefsHelper: SharedPrefsHelper
   ) : ViewModel()
{

   var product:ProductDetailsResponse? =null



   private val _placeOrderResponse:MutableSharedFlow<NetworkResource<OrderResponse>>
           = MutableSharedFlow(0)
   val placeOrderResponse: MutableSharedFlow<NetworkResource<OrderResponse>>
           = _placeOrderResponse


   fun placeOrder(productId:String,orderRequest: OrderRequest) = viewModelScope.launch(Dispatchers.IO)
   {
      _placeOrderResponse.emit( NetworkResource.Loading())
      try
      {
         val response = placeOrderUseCase.placeOrderUseCase(productId,orderRequest)
         _placeOrderResponse.emit( handleResponse(response))
      }catch (e:Exception)
      {
         when (e)
         {
            is HttpException -> _placeOrderResponse.emit(NetworkResource.Error(e.message))
            else -> _placeOrderResponse.emit(NetworkResource.Error(e.message))
         } // when closed
      }
   }

   private fun handleResponse(response: Response<OrderResponse>): NetworkResource<OrderResponse>
   {
      return when(response.code())
      {
         200,201 -> NetworkResource.Success(response.body())
         400 ,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
         else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
      } // when closed

   } // handle response



   // Get User Info

   fun getUser() = sharedPrefsHelper.getUser()


} // PlaceOrderViewModel