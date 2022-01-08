package com.example.farmersecom.features.cart.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.R
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.buyNow.domain.model.request.OrderRequest
import com.example.farmersecom.features.buyNow.domain.model.response.OrderResponse
import com.example.farmersecom.features.buyNow.domain.usecase.PlaceOrderUseCase
import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.features.cart.domain.CheckOutItem
import com.example.farmersecom.features.cart.domain.usecase.*
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.hasInternetConnection
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
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
     private val changeQuantityUseCase: ChangeQuantityUseCase,
     private val placeOrderUseCase: PlaceOrderUseCase,
     private val sharedPrefsHelper: SharedPrefsHelper,
     private val application: Application
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


    /**** Place Order for Each Item in cart **/


    private fun clearCart() = viewModelScope.launch(Dispatchers.IO)
    {
        deleteAllCartItemsUseCase.deleteAllCartItem()
    }


//    private val _placeOrderResponse:MutableStateFlow<NetworkResource<OrderResponse>>
//            = MutableStateFlow(NetworkResource.None())
//    val placeOrderResponse: StateFlow<NetworkResource<OrderResponse>>
//            = _placeOrderResponse


    private val _placeOrderResponse:MutableSharedFlow<NetworkResource<OrderResponse>>
            = MutableSharedFlow(0)
    val placeOrderResponse: MutableSharedFlow<NetworkResource<OrderResponse>>
            = _placeOrderResponse


    private val coroutineJob:Job = Job()
    private val coroutineScope =  CoroutineScope(Dispatchers.IO+coroutineJob)

    fun checkOutCart(list: List<CartItem>,checkOutItem: CheckOutItem)
    = coroutineScope.launch()
    {

        if(application.applicationContext.hasInternetConnection())
        {
            _placeOrderResponse.emit(NetworkResource.Loading())
            try
            {

                list.forEachIndexed()
                {
                        index,item ->
                    val responseAwait =
                        async { placeOrderUseCase
                            .placeOrderUseCase(item.productId,
                                OrderRequest(
                                    checkOutItem.address,
                                    checkOutItem.city,
                                    checkOutItem.name,
                                    checkOutItem.paymentOption,
                                    checkOutItem.postalCode,
                                    item.productQuantity, // quantity by item
                                    checkOutItem.contactNumber)
                            ) }
                    val actualResponse = responseAwait.await()

                    Timber.tag(TAG).d("order placed")
                    if (index==list.size-1)
                    {
                        _placeOrderResponse.emit(handleResponse(actualResponse))
                    }
                } // forEach closed
                Timber.tag(TAG).d("Cart Cleared")

                clearCart()
            }catch (e:Exception)
            {
                when (e)
                {
                    is HttpException -> _placeOrderResponse.emit(NetworkResource.Error(e.message))
                    else ->_placeOrderResponse.emit(NetworkResource.Error(e.message))
                } // when closed
            } // catch closed
        }else
        {
            _placeOrderResponse.emit(NetworkResource.Error(application.applicationContext.getString(R.string.no_internet_connection)))
        } // else closed


    //        val iterator = list.toHashSet().iterator()
//        for(cartItem in iterator)
//        {
//            if (iterator.hasNext())
//            {
//
//            }
//        }



    } // checkOutFragment closed

    private fun handleResponse(response: Response<OrderResponse>): NetworkResource<OrderResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 ,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed

    } // handle response

    // get user
    fun getUser() = sharedPrefsHelper.getUser()


    fun getAuthToken() : String? = sharedPrefsHelper.getToken()


} // CartViewModel