package com.example.farmersecom.features.buyerSection.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.features.buyerSection.data.framework.BuyerDashboardApi
import com.example.farmersecom.features.buyerSection.domain.model.orderDetails.OrderDetailsResponse
import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.buyerSection.domain.useCase.*
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
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
    private val getOrderDetailsById: GetOrderDetailsById,
    private val productRatingUseCase:RateProductUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
    private val addProductsToFavourites:AddProductToFavouritesUseCase,
    private val removeProductFromFavourites: RemoveProductFromFavourites
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


    /** Same response flow used for below requests
     * cancel order - response -> Status & Msg
     * rate a product - response -> Status & Msg
     * add product to favourites
     * remove product from favourites
     * **/


    private var _statusMsgResponse:MutableStateFlow<NetworkResource<StatusMsgResponse>>
            = MutableStateFlow(NetworkResource.None())
    val statusMsgResponse: StateFlow<NetworkResource<StatusMsgResponse>>
            = _statusMsgResponse


    fun rateProduct(productId:String,rating:Float) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val response = productRatingUseCase.rateProduct(productId,rating)
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
    } //   closed


    fun cancelOrder(orderId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val response = cancelOrderUseCase.cancelOrder(orderId)
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
    } //   closed


    fun addProductToFavourites(productId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val response = addProductsToFavourites.addProductToFavourites(productId)
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
    } //   closed

    fun removeProductsFromFavourites(productId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val response = removeProductFromFavourites.removeProductFromFavourites(productId)
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
    } //   closed



    private fun handleStatusMessageResponse(response: Response<StatusMsgResponse>): NetworkResource<StatusMsgResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    } /// HandleStatusMessageResponse closed




} /// buyerDashBoardViewModel