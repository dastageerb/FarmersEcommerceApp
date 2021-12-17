package com.example.farmersecom.features.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.features.cart.domain.usecase.GetCartItemsUseCase
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private  val getCartItemsUseCase: GetCartItemsUseCase) : ViewModel()
{


    /** Get Cart   **/
    private var _cartResponse: MutableStateFlow<NetworkResource<PagingData<JsonObject>>> =
        MutableStateFlow(NetworkResource.None());
    val cartResponse: StateFlow<NetworkResource<PagingData<JsonObject>>> = _cartResponse;
    fun getCartResponse() = viewModelScope.launch(Dispatchers.IO)
    {
        _cartResponse.value = NetworkResource.Loading()
        val response =  getCartItemsUseCase.getCartItems().cachedIn(viewModelScope)
        response.collect()
        {
            _cartResponse.value = NetworkResource.Success(it)
        }
    } // buyerOrderResponse  closed




} // CartViewModel