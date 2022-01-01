package com.example.farmersecom.features.communitySection.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.R
import com.example.farmersecom.features.communitySection.domain.models.GetPostsResponse
import com.example.farmersecom.features.communitySection.domain.usecases.GetAllCommunityPostsUseCase
import com.example.farmersecom.features.communitySection.domain.usecases.GetPostsByUserUseCase
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.editProduct.EditProduct
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.hasInternetConnection
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CommunitySectionViewModel
@Inject constructor(
    private val application: Application,
    private val getAllCommunityPostsUseCase: GetAllCommunityPostsUseCase,
    private val getPostsByUserUseCase: GetPostsByUserUseCase)
    :ViewModel()
{


    private val _getCommunityPostsResponse:MutableStateFlow<NetworkResource<GetPostsResponse>>
    = MutableStateFlow(NetworkResource.None())

    val getCommunityPostsResponse: StateFlow<NetworkResource<GetPostsResponse>>
            = _getCommunityPostsResponse


    fun getAllCommunityPosts() = viewModelScope.launch(Dispatchers.IO)
    {
        _getCommunityPostsResponse.value = NetworkResource.Loading()
        if(application.applicationContext.hasInternetConnection())
        {
           try
           {
               val response = getAllCommunityPostsUseCase.getAllCommunityPost()
               _getCommunityPostsResponse.value = handleCommunityPostsResponse(response)

           } catch (e:Exception)
           {
               when(e)
               {
                    is HttpException ->
                    {
                        _getCommunityPostsResponse.value =NetworkResource.Error(e.message)
                    }
                    else ->
                    {
                        _getCommunityPostsResponse.value =NetworkResource.Error(e.message)
                    } // else closed
                } // when closed
           } // catch closed
        } // if closed
        else
        {
            _getCommunityPostsResponse.value =NetworkResource.Error(application.applicationContext.getString(R.string.no_internet_connection))
        }
    } // getAllCommunityPosts closed

    fun getUserContributionsPosts() = viewModelScope.launch(Dispatchers.IO)
    {
        _getCommunityPostsResponse.value = NetworkResource.Loading()
        if(application.applicationContext.hasInternetConnection())
        {
            try
            {
                val response = getPostsByUserUseCase.getPostsByUser()
                _getCommunityPostsResponse.value = handleCommunityPostsResponse(response)

            } catch (e:Exception)
            {
                when(e)
                {
                    is HttpException ->
                    {
                        _getCommunityPostsResponse.value =NetworkResource.Error(e.message)
                    }
                    else ->
                    {
                        _getCommunityPostsResponse.value =NetworkResource.Error(e.message)
                    } // else closed
                } // when closed
            } // catch closed
        } // if closed
        else
        {
            _getCommunityPostsResponse.value =NetworkResource.Error(application.applicationContext.getString(R.string.no_internet_connection))
        }
    } // getUserContribution Posts closed


    private fun handleCommunityPostsResponse(response: Response<GetPostsResponse>): NetworkResource<GetPostsResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Error + ${response.code()}")
        } // when closed
    } // handleCommunityPostsResponse


    // editProduct
    private var _statusMsgResponse: MutableSharedFlow<NetworkResource<StatusMsgResponse>>
            = MutableSharedFlow(replay = 0)
    val statusMsgResponse: SharedFlow<NetworkResource<StatusMsgResponse>>
            = _statusMsgResponse


//    fun editProduct(editProduct: EditProduct, productId:String) = viewModelScope.launch(Dispatchers.IO)
//    {
//        _statusMsgResponse.emit(NetworkResource.Loading())
//        //   _statusMsgResponse.value = NetworkResource.Loading()
//        try
//        {
//            val response = editProductUseCase.editProduct(editProduct,productId)
//            _statusMsgResponse.emit(handleStatusMessageResponse(response))
//            //     _statusMsgResponse.value = handleStatusMessageResponse(response)
//        }catch (e:Exception)
//        {
//            when (e)
//            {
//                is HttpException ->{  _statusMsgResponse.emit(NetworkResource.Error(e.message)) }
//                else ->
//                {
//                    _statusMsgResponse.emit(NetworkResource.Error(e.message))
//                }
//            } // when closed
//        }
//    } //  changeProductStatus closed



    private fun handleStatusMessageResponse(response: Response<StatusMsgResponse>): NetworkResource<StatusMsgResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }




















    // Id getter and Setters

//    val setProductId :MutableStateFlow<String> = MutableStateFlow("")
//    val getProductId :StateFlow<String> = setProductId
//    fun setProductId(query:String)
//    {
//        setProductId.value = query
//    } //



} // CommunitySectionViewModel