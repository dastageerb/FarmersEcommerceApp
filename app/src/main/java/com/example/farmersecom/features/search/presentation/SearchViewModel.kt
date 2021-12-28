package com.example.farmersecom.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.home.domain.model.sliderModels.HomeSlider
import com.example.farmersecom.features.search.domain.model.SearchResponse
import com.example.farmersecom.features.search.domain.model.categories.CategoriesResponse
import com.example.farmersecom.features.search.domain.useCases.GetAllCategories
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
class SearchViewModel @Inject constructor(private val searchProductUseCase: SearchProductUseCase,
                                            private val getAllCategories: GetAllCategories
                                            ,private val sharedPrefsHelper: SharedPrefsHelper) :ViewModel()
{


    private var _searchResponse:MutableStateFlow<NetworkResource<SearchResponse>> =
        MutableStateFlow(NetworkResource.None());

     val searchResponse:StateFlow<NetworkResource<SearchResponse>> = _searchResponse;

    fun searchProduct(query:String, category:String?=null, location:String?=null) = viewModelScope.launch(Dispatchers.IO)
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





    private var _categoriesResponse:MutableStateFlow<NetworkResource<CategoriesResponse>>
            = MutableStateFlow(NetworkResource.None())
    val categoriesResponse: StateFlow<NetworkResource<CategoriesResponse>>
            = _categoriesResponse


    fun getAllCategories() = viewModelScope.launch(Dispatchers.IO)
    {
        _categoriesResponse.value = NetworkResource.Loading()
        try
        {
            val response = getAllCategories.getAllCategories()
            _categoriesResponse.value = handleCategoriesResponse(response)

        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException ->  _categoriesResponse.value = NetworkResource.Error("Something went wrong")
                else ->
                {
                    _categoriesResponse.value = NetworkResource.Error(""+e.message)
                }
            } // when closed
        }
    }

    private fun handleCategoriesResponse(response: Response<CategoriesResponse>): NetworkResource<CategoriesResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }



    //  Filters Methods

    fun saveLocation(location: String) =sharedPrefsHelper.saveLocation(location)
    fun saveCategory(category: String) =sharedPrefsHelper.saveCategory(category)
    fun getLocation() = sharedPrefsHelper.getLocation()
    fun getCategory() = sharedPrefsHelper.getCategory()

    fun clearFilters() = sharedPrefsHelper.clearFilters()






} // searchViewModel closed