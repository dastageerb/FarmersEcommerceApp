package com.example.farmersecom.features.home.presentation

import android.app.slice.SliceItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.RegisterResponse
import com.example.farmersecom.features.home.domain.model.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.HomeSlider
import com.example.farmersecom.features.home.domain.model.sharedModel.CategoryItem
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
class SharedViewModel @Inject constructor():ViewModel()
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



} //