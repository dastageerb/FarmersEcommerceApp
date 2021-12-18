package com.example.farmersecom.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.farmersecom.features.home.domain.model.MoreProductsResponseItem
import com.example.farmersecom.features.home.domain.model.homeModels.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.more.MoreProduct
import com.example.farmersecom.features.home.domain.model.more.MoreResponseItem
import com.example.farmersecom.features.home.domain.model.sharedModel.CategoryItem
import com.example.farmersecom.features.home.domain.usecases.GetMoreCategoryItemsUseCase
import com.example.farmersecom.features.home.domain.usecases.GetMoreSliderItemsUseCase
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getMoreSliderItemsUseCase: GetMoreSliderItemsUseCase,
    private val getMoreCategoryItemsUseCase: GetMoreCategoryItemsUseCase):ViewModel()
{


    /** Getters Setters For Shared Communication  **/

    // for  Slider Items
    val setSliderQuery :MutableStateFlow<String> = MutableStateFlow("")
    val getSliderQuery :StateFlow<String> = setSliderQuery
    fun setSliderQuery(query:String)
    {
        setSliderQuery.value = query
    } // setSliderQuery

    // for Latest Items by Category (See All)

    val setCategoryItem :MutableStateFlow<CategoryItem> = MutableStateFlow(CategoryItem("",""))
    val getCategoryItem :StateFlow<CategoryItem> = setCategoryItem
    fun setCategoryItem(categoryItem: CategoryItem)
    {
        setCategoryItem.value = categoryItem
    } // setSliderQuery

    /** Get More Slider Items **/

    private var _moreItemsResponseProduct:MutableStateFlow<NetworkResource<MoreResponseItem>> =
        MutableStateFlow(NetworkResource.None());

    val moreItemsResponseProduct :StateFlow<NetworkResource<MoreResponseItem>> = _moreItemsResponseProduct


    fun getMoreSliderItems(query:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _moreItemsResponseProduct.value = NetworkResource.Loading()
        try
        {
           val response = getMoreSliderItemsUseCase.getMoreSliderItems(query)
            _moreItemsResponseProduct.value = handleResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _moreItemsResponseProduct.value= NetworkResource.Error("Something went wrong")
                else -> _moreItemsResponseProduct.value = NetworkResource.Error("No Internet Connection")
            } // when closed
        }
    }

    fun getMoreCategoryItems(categoryId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _moreItemsResponseProduct.value = NetworkResource.Loading()
        try
        {
            val response = getMoreCategoryItemsUseCase.getMoreCategoryItems(categoryId)
            _moreItemsResponseProduct.value = handleResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _moreItemsResponseProduct.value= NetworkResource.Error("Something went wrong")
                else -> _moreItemsResponseProduct.value = NetworkResource.Error("No Internet Connection")
            } // when closed
        }
    }

    private fun handleResponse(response: Response<MoreResponseItem>): NetworkResource<MoreResponseItem>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }


//    @InternalCoroutinesApi
//    fun moreSliderItems(query:String) = viewModelScope.launch(Dispatchers.IO)
//    {
//        _moreSliderItemsResponseProduct.value = NetworkResource.Loading()
//        val response =  getMoreSliderItemsUseCase.getMoreSliderItems(query).cachedIn(viewModelScope)
//        response.collect ()
//        {
//            _moreSliderItemsResponseProduct.value = NetworkResource.Success(it)
//        }
//
//    } // searchItem closed



} //