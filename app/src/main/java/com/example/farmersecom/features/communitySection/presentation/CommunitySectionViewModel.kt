package com.example.farmersecom.features.communitySection.presentation

import android.app.Application
import android.content.Context
import android.icu.text.CaseMap
import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.R
import com.example.farmersecom.features.communitySection.domain.models.GetPostsResponse
import com.example.farmersecom.features.communitySection.domain.models.Post
import com.example.farmersecom.features.communitySection.domain.usecases.*
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.features.storeAdmin.domain.model.editProduct.EditProduct
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.hasInternetConnection
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CommunitySectionViewModel
@Inject constructor(
    private val application: Application,
    private val getAllCommunityPostsUseCase: GetAllCommunityPostsUseCase,
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val getPostsByUserUseCase: GetPostsByUserUseCase,
    private val getSearchedPostsUseCase: GetSearchedPostsUseCase,
    private val addCommunityPostUseCase: AddCommunityPostUseCase,
    private val updateCommunityPostUseCase: UpdateCommunityPostUseCase,
    private val deleteCommunityPostUseCase: DeletePostUseCase
    )
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







    private val _getPostByIdResponse:MutableStateFlow<NetworkResource<Post>>
            = MutableStateFlow(NetworkResource.None())

    val getPostByIdResponse: StateFlow<NetworkResource<Post>>
            = _getPostByIdResponse


    fun getPostById(postId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _getPostByIdResponse.value = NetworkResource.Loading()
        if(application.applicationContext.hasInternetConnection())
        {
            try
            {
                val response = getPostByIdUseCase.getPostByIdUseCase(postId)
                _getPostByIdResponse.value = handlePostByIdResponse(response)

            } catch (e:Exception)
            {
                when(e)
                {
                    is HttpException ->
                    {
                        _getPostByIdResponse.value =NetworkResource.Error(e.message)
                    }
                    else ->
                    {
                        _getPostByIdResponse.value =NetworkResource.Error(e.message)
                    } // else closed
                } // when closed
            } // catch closed
        } // if closed
        else
        {
            _getCommunityPostsResponse.value =NetworkResource.Error(application.applicationContext.getString(R.string.no_internet_connection))
        }
    } // getAllCommunityPosts closed

    private fun handlePostByIdResponse(response: Response<Post>): NetworkResource<Post>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400,404 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Error + ${response.code()}")
        } // when closed
    }


    // edit pose // delete post // add post ->  responses
    private var _statusMsgResponse: MutableSharedFlow<NetworkResource<StatusMsgResponse>>
            = MutableSharedFlow(replay = 0)
    val statusMsgResponse: SharedFlow<NetworkResource<StatusMsgResponse>>
            = _statusMsgResponse


    fun addPost(title:String,description:String ,imageUri: Uri) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.emit(NetworkResource.Loading())
        //   _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val response = addCommunityPostUseCase.addCommunityPost(title,description,convertImageToMultiPart(imageUri))
            _statusMsgResponse.emit(handleStatusMessageResponse(response))
            //     _statusMsgResponse.value = handleStatusMessageResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException ->{  _statusMsgResponse.emit(NetworkResource.Error(e.message)) }
                else ->
                {
                    _statusMsgResponse.emit(NetworkResource.Error(e.message))
                }
            } // when closed
        }
    } //  changeProductStatus closed



    fun editORUpdatePost(postId:String,title:String,description:String ,imageUri: Uri?=null) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.emit(NetworkResource.Loading())
        //   _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val response = updateCommunityPostUseCase.updateCommunityPost(postId,title,description,convertImageToMultiPart(imageUri))
            _statusMsgResponse.emit(handleStatusMessageResponse(response))
            //     _statusMsgResponse.value = handleStatusMessageResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException ->{  _statusMsgResponse.emit(NetworkResource.Error(e.message)) }
                else ->
                {
                    _statusMsgResponse.emit(NetworkResource.Error(e.message))
                }
            } // when closed
        }
    } //  changeProductStatus closed



    fun deletePost(postId:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.emit(NetworkResource.Loading())
        //   _statusMsgResponse.value = NetworkResource.Loading()
        try
        {
            val responseWait = async { deleteCommunityPostUseCase.deletePost(postId) }

            val response = responseWait.await()

            getUserContributionsPosts()

            _statusMsgResponse.emit(handleStatusMessageResponse(response))
            //     _statusMsgResponse.value = handleStatusMessageResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException ->{  _statusMsgResponse.emit(NetworkResource.Error(e.message)) }
                else ->
                {
                    _statusMsgResponse.emit(NetworkResource.Error(e.message))
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
            else -> NetworkResource.Error("Error + ${response.code()}")
        } // when closed
    }




















    // Id getter and Setters

    private val setPostId :MutableLiveData<String> = MutableLiveData()
    val getPostId :LiveData<String> = setPostId
    fun setPostId(id:String)
    {
        setPostId.value = id
    } //

    // util method


    private fun convertImageToMultiPart(imageUri: Uri? =null): MultipartBody.Part
    {
        val file = imageUri?.toFile()
        val requestFile = file?.asRequestBody(application.applicationContext.contentResolver.getType(imageUri)?.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file?.path, requestFile!!)
    }



} // CommunitySectionViewModel