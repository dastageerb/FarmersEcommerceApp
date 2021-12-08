package com.example.farmersecom.features.buyerSection.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.features.buyerSection.domain.useCase.GetBuyerFavouritesUseCase
import com.example.farmersecom.features.buyerSection.domain.useCase.GetBuyerNotificationUseCase
import com.example.farmersecom.features.buyerSection.domain.useCase.GetBuyerOrderByStatusUseCase
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@InternalCoroutinesApi
class BuyerDashboardViewModel @Inject constructor(
    private val getBuyerOrderByStatusUseCase: GetBuyerOrderByStatusUseCase,
    private val getBuyerNotificationUseCase: GetBuyerNotificationUseCase,
    private val getBuyerFavouritesUseCase: GetBuyerFavouritesUseCase) :ViewModel()
{

    /** Get Buyer Orders By Status  **/
    private var _buyerOrdersResponse: MutableStateFlow<NetworkResource<PagingData<JsonObject>>> =
        MutableStateFlow(NetworkResource.None());
    val buyerOrderResponse:StateFlow<NetworkResource<PagingData<JsonObject>>> = _buyerOrdersResponse;
    fun getBuyerOrderByStatus(orderStatus:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _buyerOrdersResponse.value = NetworkResource.Loading()
        val response =  getBuyerOrderByStatusUseCase.getBuyerOrderByStatus(orderStatus).cachedIn(viewModelScope)
        response.collect()
        {
            _buyerOrdersResponse.value = NetworkResource.Success(it)
        }
    } // buyerOrderResponse  closed



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








} /// buyerDashBoardViewModel