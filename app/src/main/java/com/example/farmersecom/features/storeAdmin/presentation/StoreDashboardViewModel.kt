package com.example.farmersecom.features.storeAdmin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.features.buyerSection.domain.model.orderDetails.OrderDetailsResponse
import com.example.farmersecom.features.buyerSection.domain.model.orderStatus.OrderStatusResponse
import com.example.farmersecom.features.buyerSection.domain.useCase.GetOrderDetailsById
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.productStatusResponse.ProductStatus
import com.example.farmersecom.features.storeAdmin.domain.useCases.ChangeOrderStatusUseCase
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.features.storeAdmin.domain.useCases.GetOrdersByStatusUseCase
import com.example.farmersecom.features.storeAdmin.domain.useCases.GetProductsByStatusUseCase
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StoreDashboardViewModel @Inject constructor(
    private  val getProductsByStatusUseCase: GetProductsByStatusUseCase,
    private val getOrdersByStatusUseCase: GetOrdersByStatusUseCase,
    private val getOrderDetailsById: GetOrderDetailsById,
    private val changeOrderStatusUseCase: ChangeOrderStatusUseCase
) : ViewModel()
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
            Timber.tag(Constants.TAG).d(""+response.body())
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


    /** Get products by status **/


    private var _productByStatusResponse:MutableStateFlow<NetworkResource<List<ProductStatus>>> =
        MutableStateFlow(NetworkResource.None());

    val productByStatusResponse:StateFlow<NetworkResource<List<ProductStatus>>> = _productByStatusResponse;


    fun getProductByStatus(isActive:Boolean) = viewModelScope.launch(Dispatchers.IO)
    {
        _productByStatusResponse.value = NetworkResource.Loading()
        try
        {
            val response = getProductsByStatusUseCase.getProductsByStatus(isActive)
            _productByStatusResponse.value = handleProductResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _productByStatusResponse.value = NetworkResource.Error("Http Exception")
                else -> _productByStatusResponse.value = NetworkResource.Error("${e.message}")
            }
        } //

    } // productByStatus closed

    private fun handleProductResponse(response: Response<List<ProductStatus>>): NetworkResource<List<ProductStatus>>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    }


    /** Get Buyer Orders By Status  **/
    private var _sellerOrdersResponse: MutableStateFlow<NetworkResource<OrderStatusResponse>> =
        MutableStateFlow(NetworkResource.None());
    val sellerOrderResponse: StateFlow<NetworkResource<OrderStatusResponse>> = _sellerOrdersResponse;
    fun getSellerOrders(isActive:Boolean) = viewModelScope.launch(Dispatchers.IO)
    {
        _sellerOrdersResponse.value = NetworkResource.Loading()
        try
        {
            val response = getOrdersByStatusUseCase.getProductsByStatus(isActive)

            _sellerOrdersResponse.value = handleSellerOrdersResponse(response)
        }catch (e:Exception)
        {
            _sellerOrdersResponse.value = NetworkResource.Error("${e.message}")
            when (e)
            {
                is HttpException -> _sellerOrdersResponse.value = NetworkResource.Error("Http Exception"+e.message())
                else -> _sellerOrdersResponse.value =
                    NetworkResource.Error("No Internet Connection: ${e.message}")
            }
        } //
    } // getProfile closed

    private fun handleSellerOrdersResponse(response: Response<OrderStatusResponse>): NetworkResource<OrderStatusResponse>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    }


     /** Change Order Status  */

     private var _statusMsgResponse:MutableStateFlow<NetworkResource<StatusMsgResponse>>
             = MutableStateFlow(NetworkResource.None())
    val statusMsgResponse: StateFlow<NetworkResource<StatusMsgResponse>>
            = _statusMsgResponse


    fun changeOrderStatus(status:String,orderId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val responseDeffered = async { changeOrderStatusUseCase.changeOrderStatus(status, orderId) }

            val response = responseDeffered.await()
            orderDetails(orderId)

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
    } //  changeProductStatus closed



    private fun handleStatusMessageResponse(response: Response<StatusMsgResponse>): NetworkResource<StatusMsgResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }






} // RegisterViewModel closed