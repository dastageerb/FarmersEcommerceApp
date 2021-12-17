package com.example.farmersecom.features.home.presentation

import android.app.slice.SliceItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.RegisterResponse
import com.example.farmersecom.features.home.domain.model.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.HomeSlider
import com.example.farmersecom.features.home.domain.usecases.GetHomeLatestItemsUseCase
import com.example.farmersecom.features.home.domain.usecases.GetSliderItemsUseCase
import com.example.farmersecom.features.productDetails.domain.model.ProductDetailsResponse
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSliderItemsUseCase: GetSliderItemsUseCase,
    private val getHomeLatestItemsUseCase: GetHomeLatestItemsUseCase):ViewModel()
{

    /** Get Slider Items */
    private val _getSliderItemsResponse:MutableStateFlow<NetworkResource<List<HomeSlider>>>
            = MutableStateFlow(NetworkResource.None())
    val getSliderItemsResponse: StateFlow<NetworkResource<List<HomeSlider>>>
            = _getSliderItemsResponse


    fun getSliderItems() = viewModelScope.launch(Dispatchers.IO)
    {
        _getSliderItemsResponse.value = NetworkResource.Loading()
        try
        {
            val response = getSliderItemsUseCase.getSliderItems()
            _getSliderItemsResponse.value = handleSliderResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _getSliderItemsResponse.value = NetworkResource.Error("Something went wrong")
                else -> _getSliderItemsResponse.value = NetworkResource.Error("No Internet Connection")
            } // when closed
        } // catch closed
    } // getSliderItems closed
    private fun handleSliderResponse(response: Response<List<HomeSlider>>): NetworkResource<List<HomeSlider>>
    {

        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    } // handleSliderResponse


    /** Get Home Latest Items Items */
    private val _getHomeLatestResponse:MutableStateFlow<NetworkResource<List<HomeLatestItem>>>
            = MutableStateFlow(NetworkResource.None())
    val getHomeLatestResponse: StateFlow<NetworkResource<List<HomeLatestItem>>>
            = _getHomeLatestResponse

    fun getHomeLatestItems() = viewModelScope.launch(Dispatchers.IO)
    {
        _getHomeLatestResponse.value = NetworkResource.Loading()
        try
        {
            val response = getHomeLatestItemsUseCase.getHomeLatestItemsUseCase()
            _getHomeLatestResponse.value = handleLatestItemsResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _getHomeLatestResponse.value= NetworkResource.Error("Something went wrong")
                else -> _getHomeLatestResponse.value = NetworkResource.Error("No Internet Connection")
            } // when closed
        }
    }

    private fun handleLatestItemsResponse(response: Response<List<HomeLatestItem>>): NetworkResource<List<HomeLatestItem>>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }


} //