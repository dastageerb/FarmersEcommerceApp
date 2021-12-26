package com.example.farmersecom.features.storeAdmin.presentation.storeSettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.features.productStore.domain.model.storeDetails.StoreDetailsResponse
import com.example.farmersecom.features.productStore.domain.usecase.GetStoreByIdUseCase
import com.example.farmersecom.features.productStore.domain.usecase.GetStoreProductsByStoreIdUseCase
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.features.productStore.domain.model.storeProducts.Product
import com.example.farmersecom.features.productStore.domain.model.storeProducts.StoreProducts
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.changeStoreImage.ChangeStoreImageResponse
import com.example.farmersecom.features.storeAdmin.domain.useCases.EditStoreCashOnDeliveryService
import com.example.farmersecom.features.storeAdmin.domain.useCases.GetStoreDetailsUseCase
import com.example.farmersecom.features.storeAdmin.domain.useCases.UpdateStoreImageUseCase
import com.example.farmersecom.features.storeAdmin.domain.useCases.UpdateStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StoreSettingViewModel @Inject constructor
    (private val getStoreDetailsUseCase: GetStoreDetailsUseCase
    ,private val updateStoreImageUseCase: UpdateStoreImageUseCase
    ,private val updateStoreUseCase: UpdateStoreUseCase
    ,private val editStoreCashOnDeliveryService: EditStoreCashOnDeliveryService) : ViewModel()
{


    /** Get Store Details Response **/
    private val _productStoreResponse: MutableStateFlow<NetworkResource<StoreDetailsResponse>> = MutableStateFlow(
        NetworkResource.None())
    val productStoreResponse: StateFlow<NetworkResource<StoreDetailsResponse>> = _productStoreResponse


    fun getStoreDetails() = viewModelScope.launch(Dispatchers.IO)
    {
        _productStoreResponse.value = NetworkResource.Loading()
        try
        {
            val response = getStoreDetailsUseCase.getStoreDetails()
            _productStoreResponse.value = handleStoreDetailsResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _productStoreResponse.value = NetworkResource.Error("Http Exception")
                else -> _productStoreResponse.value =
                    NetworkResource.Error("No Internet Connection: ${e.message}")
            }
        } //
    } // getProfile closed

    private fun handleStoreDetailsResponse(response: Response<StoreDetailsResponse>): NetworkResource<StoreDetailsResponse>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    } // handleStoreDetailsResponse




    private val _storeImageChangedResponse: MutableStateFlow<NetworkResource<ChangeStoreImageResponse>> = MutableStateFlow(
        NetworkResource.None())
    val storeImageChangedResponse: StateFlow<NetworkResource<ChangeStoreImageResponse>> = _storeImageChangedResponse


    fun updateStoreImage(file:MultipartBody.Part) = viewModelScope.launch(Dispatchers.IO)
    {
        _productStoreResponse.value = NetworkResource.Loading()
        try
        {
            val response = updateStoreImageUseCase.updateStoreImageUseCase(file)
            _storeImageChangedResponse.value = handleImageUpdateResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _storeImageChangedResponse.value = NetworkResource.Error("Http Exception")
                else -> _storeImageChangedResponse.value =
                    NetworkResource.Error("No Internet Connection: ${e.message}")
            }
        } //
    } // getProfile closed

    private fun handleImageUpdateResponse(response: Response<ChangeStoreImageResponse>): NetworkResource<ChangeStoreImageResponse>
    {
        return when(response.code())
        {
            200 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went wrong ${response.code()}")
        } // when closed
    } // handleStoreDetailsResponse


    /** update Store and store deliveryMethod contain same response ( status , message )
     *there for using same flow for  getting response **/


    private var _statusMsgResponse:MutableStateFlow<NetworkResource<StatusMsgResponse>>
            = MutableStateFlow(NetworkResource.None())
    val getStatusMsgResponse: StateFlow<NetworkResource<StatusMsgResponse>>
            = _statusMsgResponse

    fun updateStore(name:String,desc:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val responseDeffered = async { updateStoreUseCase.updateStoreUseCase(name,desc)}

            val response = responseDeffered.await()
            getStoreDetails()
          _statusMsgResponse.value = handleStatusResponse(response)

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


    fun updateStoreDelivery(boolean: Boolean) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val response = editStoreCashOnDeliveryService.editCashOnDeliveryService(boolean)
            _statusMsgResponse.value = handleStatusResponse(response)

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



    private fun handleStatusResponse(response: Response<StatusMsgResponse>): NetworkResource<StatusMsgResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }




} // ProductStoreViewModel closed