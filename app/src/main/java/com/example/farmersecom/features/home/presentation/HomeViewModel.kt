package com.example.farmersecom.features.home.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.R
import com.example.farmersecom.features.home.data.framework.HomeApi
import com.example.farmersecom.features.home.domain.model.homeModels.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.sliderModels.HomeSlider
import com.example.farmersecom.features.home.domain.usecases.GetHomeLatestItemsUseCase
import com.example.farmersecom.features.home.domain.usecases.GetSliderItemsUseCase
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.hasInternetConnection
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSliderItemsUseCase: GetSliderItemsUseCase,
    private val getHomeLatestItemsUseCase: GetHomeLatestItemsUseCase,
    private val application: Application
    ):ViewModel()
{

    /** Get Slider Items */
    private val _getSliderItemsResponse:MutableStateFlow<NetworkResource<List<HomeSlider>>>
            = MutableStateFlow(NetworkResource.None())
    val getSliderItemsResponse: StateFlow<NetworkResource<List<HomeSlider>>>
            = _getSliderItemsResponse


    fun getSliderItems() = viewModelScope.launch(Dispatchers.IO)
    {

        if(application.applicationContext.hasInternetConnection())
        {

            _getSliderItemsResponse.value = NetworkResource.Loading()

            try
            {
                val responseDef = async {   getSliderItemsUseCase.getSliderItems() }
                val response = responseDef.await()
                getHomeLatestItems()
                _getSliderItemsResponse.value = handleSliderResponse(response)
            }catch (e:Exception)
            {
                when (e)
                {
                    is HttpException -> _getSliderItemsResponse.value = NetworkResource.Error(e.message())
                    else -> _getSliderItemsResponse.value = NetworkResource.Error(e.message)
                } // when closed
            } // catch closed
        }else
        {
            _getSliderItemsResponse.value = NetworkResource.Error(application.applicationContext.getString(R.string.no_internet_connection))
        } // else closed
    } // getSliderItems closed


    private fun handleSliderResponse(response: Response<List<HomeSlider>>): NetworkResource<List<HomeSlider>>
    {

        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else ->NetworkResource.Error(application.applicationContext.getString(R.string.something_went_wrong)+response.code())
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

//     fun getHomeInfo() = viewModelScope.launch(Dispatchers.IO)
//    {
//        try
//        {
//
//            val response = homeApi.getMoreSliderItems("mango",1,20)
//            Timber.tag(TAG).d("${response.body().toString()}")
//        }catch (e:Exception)
//        {
//            Timber.tag(TAG).d("${e.message}")
//        }
//    }

} //