package com.example.farmersecom.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.features.home.domain.model.sliderModels.HomeSlider
import com.example.farmersecom.features.search.domain.model.SearchResponse
import com.example.farmersecom.features.search.domain.useCases.SearchProductUseCase
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchProductUseCase: SearchProductUseCase) :ViewModel()
{

    private var _searchResponse:MutableStateFlow<NetworkResource<SearchResponse>> =
        MutableStateFlow(NetworkResource.None());

     val searchResponse:StateFlow<NetworkResource<SearchResponse>> = _searchResponse;

    fun searchProduct(query:String, category:String, location:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _searchResponse.value = NetworkResource.Loading()

        try
        {
            val response = searchProductUseCase.searchItem(query,category,location)
            _searchResponse.value = handleResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _searchResponse.value = NetworkResource.Error("Something went wrong")
                else -> _searchResponse.value = NetworkResource.Error("No Internet Connection")
            } // when closed
        } // catch closed

    } // searchItem closed

    private fun handleResponse(response: Response<SearchResponse>): NetworkResource<SearchResponse>
    {

        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed

    } // handleResponse

} // searchViewModel closed