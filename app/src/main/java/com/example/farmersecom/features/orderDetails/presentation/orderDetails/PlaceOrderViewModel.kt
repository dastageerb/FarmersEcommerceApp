package com.example.farmersecom.features.orderDetails.presentation.orderDetails

import androidx.lifecycle.ViewModel
import com.example.farmersecom.features.cart.domain.usecase.GetCartItemsUseCase
import com.example.farmersecom.features.orderDetails.domain.usecase.PlaceOrderUseCase
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(private val placeOrderUseCase: PlaceOrderUseCase) : ViewModel()
{

    val setOrderItems :MutableStateFlow<List<ProductDetailsResponse>> = MutableStateFlow(emptyList())

    val getOrderItems :StateFlow<List<ProductDetailsResponse>> = setOrderItems

    fun setList( list: List<ProductDetailsResponse>)
    {
        setOrderItems.value = list
    }

    fun getOrderItems():Flow<List<ProductDetailsResponse>>
    {
        return getOrderItems
    }



} // PlaceOrderViewModel