package com.example.farmersecom.features.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.features.cart.domain.usecase.*
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor
    (private  val getCartItemsUseCase: GetCartItemsUseCase,
    private val getSubTotalUseCase: GetSubTotalUseCase,
    private val getTotalPriceUseCase: GetTotalPriceUseCase,
    private val getTotalDeliveryChargesUseCase: GetTotalDeliveryChargesUseCase,
    private val insertCartItemUseCase: InserCartItemUseCase,
     private val deleteCartItemUseCase: DeleteCartItemUseCase,
     private val deleteAllCartItemsUseCase: DeleteAllCartItemsUseCase,
     private val changeQuantityUseCase: ChangeQuantityUseCase
    ) : ViewModel()
{


    /** Get Cart   **/
     val getAllCartItems: Flow<List<CartItem>> = getCartItemsUseCase.getCartItems()

    // sub total  = price without delivery charges
    val getSubtotal: Flow<Int> = getSubTotalUseCase.getSubTotal()
    // get total price including delivery charges
    val getTotal: Flow<Int> = getTotalPriceUseCase.getTotal()
    // get total shipping cost
    val getDeliveryCharges: Flow<Int> = getTotalDeliveryChargesUseCase.getTotalDeliveryCharges()


    fun insertCartItem(cartItem: CartItem) = viewModelScope.launch(Dispatchers.IO)
    {
        insertCartItemUseCase.insertCartItem(cartItem)
    }


    fun deleteCartItem(cartItem: CartItem) = viewModelScope.launch(Dispatchers.IO)
    {
        deleteCartItemUseCase.deleteCartItem(cartItem)
    }

    fun changeQuantity(productId:String,quantity:Int) = viewModelScope.launch(Dispatchers.IO)
    {
        changeQuantityUseCase.changeQuantityUseCase(productId,quantity)
    }


    fun clearCart() = viewModelScope.launch(Dispatchers.IO)
    {
        deleteAllCartItemsUseCase.deleteAllCartItem()
    }




} // CartViewModel