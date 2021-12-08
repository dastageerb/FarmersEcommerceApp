package com.example.farmersecom.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.features.search.domain.model.SearchItem
import com.example.farmersecom.features.search.domain.useCases.SearchProductUseCase
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchProductUseCase: SearchProductUseCase) :ViewModel()
{

    private var _searchResponse:MutableStateFlow<NetworkResource<PagingData<SearchItem>>> =
        MutableStateFlow(NetworkResource.None());

     val searchResponse = _searchResponse;

    @InternalCoroutinesApi
    fun searchProduct(query:String, category:String, location:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _searchResponse.value = NetworkResource.Loading()

        val response =  searchProductUseCase.searchItem(query,category,location).cachedIn(viewModelScope)

        response.collect()
        {
            _searchResponse.value = NetworkResource.Success(it)
        }

    } // searchItem closed



} // searchViewModel closed