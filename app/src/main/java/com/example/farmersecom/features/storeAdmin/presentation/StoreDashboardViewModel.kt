package com.example.farmersecom.features.storeAdmin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.features.storeAdmin.domain.model.OrderStatus
import com.example.farmersecom.features.storeAdmin.domain.model.ProductStatus
import com.example.farmersecom.features.storeAdmin.domain.useCases.GetOrdersByStatusUseCase
import com.example.farmersecom.features.storeAdmin.domain.useCases.GetProductsByStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class StoreDashboardViewModel @Inject constructor(
    private  val getProductsByStatusUseCase: GetProductsByStatusUseCase,
private val getOrdersByStatusUseCase: GetOrdersByStatusUseCase
) : ViewModel()
{


    private var _productByStatusResponse:MutableStateFlow<NetworkResource<PagingData<ProductStatus>>> =
        MutableStateFlow(NetworkResource.None());

    val productByStatusResponse = _productByStatusResponse;


    fun getProductByStatus(isActive:Boolean) = viewModelScope.launch(Dispatchers.IO)
    {
        _productByStatusResponse.value = NetworkResource.Loading()

        val response =  getProductsByStatusUseCase.getProductsByStatus(isActive).cachedIn(viewModelScope)

        response.collect()
        {
            _productByStatusResponse.value = NetworkResource.Success(it)
        }

    } // productByStatus closed



    private var _orderByStatusResponse:MutableStateFlow<NetworkResource<PagingData<OrderStatus>>> =
        MutableStateFlow(NetworkResource.None());

    val orderByStatusResponse = _productByStatusResponse;

    @InternalCoroutinesApi
    fun getOrdersByStatus(orderStatus:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _orderByStatusResponse.value = NetworkResource.Loading()
        val response =  getOrdersByStatusUseCase.getProductsByStatus(orderStatus).cachedIn(viewModelScope)
        response.collect()
        {
            _orderByStatusResponse.value = NetworkResource.Success(it)
        }
    } // productByStatus closed




} // RegisterViewModel closed