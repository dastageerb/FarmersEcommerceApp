package com.example.farmersecom.features.buyerSection.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.features.buyerSection.data.framework.BuyerDashboardApi
import com.example.farmersecom.features.buyerSection.domain.model.orderDetails.OrderDetailsResponse
import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.buyerSection.domain.useCase.GetBuyerFavouritesUseCase
import com.example.farmersecom.features.buyerSection.domain.useCase.GetBuyerNotificationUseCase
import com.example.farmersecom.features.buyerSection.domain.useCase.GetBuyerOrderByStatusUseCase
import com.example.farmersecom.features.buyerSection.domain.useCase.GetOrderDetailsById
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BuyerDashboardViewModel @Inject constructor(
    private val getBuyerOrderByStatusUseCase: GetBuyerOrderByStatusUseCase,
    private val getBuyerNotificationUseCase: GetBuyerNotificationUseCase,
    private val getBuyerFavouritesUseCase: GetBuyerFavouritesUseCase,
    private val getOrderDetailsById: GetOrderDetailsById
    ) :ViewModel()
{

    // for communication between fragments
    private val setOrderId :MutableStateFlow<String> = MutableStateFlow("")
    val getOrderId :StateFlow<String> = setOrderId
    fun setOrderId(query:String)
    {
        setOrderId.value = query
    } //




    /** Get Buyer Orders By Status  **/
    private var _orderDetailsResponse: MutableStateFlow<NetworkResource<OrderDetailsResponse>> =
        MutableStateFlow(NetworkResource.None());
    val orderDetailsResponse:StateFlow<NetworkResource<OrderDetailsResponse>> =_orderDetailsResponse;
    fun orderDetails(orderId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _orderDetailsResponse.value = NetworkResource.Loading()
        try
        {
            val response = getOrderDetailsById.getOrderDetailsById(orderId)
            Timber.tag(TAG).d(""+response.body())
            _orderDetailsResponse.value = handleOrderDetailsResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _orderDetailsResponse.value = NetworkResource.Error("Http Exception")
                else -> _orderDetailsResponse.value = NetworkResource.Error("${e.message}")
            }
        } //
    } // getProfile closed

    private fun handleOrderDetailsResponse(response: Response<OrderDetailsResponse>): NetworkResource<OrderDetailsResponse>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    } // handleOrderDetailsResponse


    /** Get Buyer Orders By Status  **/
    private var _buyerOrdersResponse: MutableStateFlow<NetworkResource<OrderStatusResponse>> =
        MutableStateFlow(NetworkResource.None());
    val buyerOrderResponse:StateFlow<NetworkResource<OrderStatusResponse>> = _buyerOrdersResponse;
    fun getBuyerOrders(isActive:Boolean) = viewModelScope.launch(Dispatchers.IO)
    {
        _buyerOrdersResponse.value = NetworkResource.Loading()
        try
        {
            val response = getBuyerOrderByStatusUseCase.getBuyerOrderByStatus(isActive)
            Timber.tag(TAG).d(""+response.body())
            _buyerOrdersResponse.value = handleBuyerOrdersResponse(response)
        }catch (e:Exception)
        {
            _buyerOrdersResponse.value = NetworkResource.Error("${e.message}")
            when (e)
            {
                  is HttpException -> _buyerOrdersResponse.value = NetworkResource.Error("Http Exception")
                else -> _buyerOrdersResponse.value =
                    NetworkResource.Error("${e.message}")
            }
        } //
    } // getProfile closed

    private fun handleBuyerOrdersResponse(response: Response<OrderStatusResponse>): NetworkResource<OrderStatusResponse>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    }


    /** Get Buyer Favourites Products  **/
    private var _buyerFavouritesResponse: MutableStateFlow<NetworkResource<PagingData<JsonObject>>> =
        MutableStateFlow(NetworkResource.None());
    val buyerFavouritesResponse = _buyerFavouritesResponse;
    fun getBuyerFavourites() = viewModelScope.launch(Dispatchers.IO)
    {
        _buyerFavouritesResponse.value = NetworkResource.Loading()
        val response =  getBuyerFavouritesUseCase.getBuyerFavourites().cachedIn(viewModelScope)
        response.collect()
        {
            _buyerFavouritesResponse.value = NetworkResource.Success(it)
        }
    } // buyerFavourites closed


    /** Get Buyer Notifications Products  **/
    private var _buyerNotificationsResponse: MutableStateFlow<NetworkResource<PagingData<JsonObject>>> =
        MutableStateFlow(NetworkResource.None());
    val buyerNotifications = _buyerNotificationsResponse;
    fun getBuyerNotifications() = viewModelScope.launch(Dispatchers.IO)
    {
        _buyerNotificationsResponse.value = NetworkResource.Loading()
        val response =  getBuyerNotificationUseCase.getBuyerNotifications().cachedIn(viewModelScope)
        response.collect()
        {
            _buyerNotificationsResponse.value = NetworkResource.Success(it)
        }
    } // buyerNotifications closed




// fun getResponse() = viewModelScope.launch(Dispatchers.IO)
// {
//     try
//     {
//         val response = buyerDashboardApi.getOrders(true)
//         Timber.tag(TAG).d("${response.body()}")
//         Timber.tag(TAG).d("${response.code()}")
//
//     }catch (e:java.lang.Exception)
//     {
//         Timber.tag(TAG).d("${e}")
//     }
//
//
// }





} /// buyerDashBoardViewModel