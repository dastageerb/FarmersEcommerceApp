package com.example.farmersecom.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.SharedPrefsHelper
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.User
import com.example.farmersecom.features.cart.domain.usecase.DeleteAllCartItemsUseCase
import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.features.profile.domain.model.ChangePhotoResponse
import com.example.farmersecom.features.profile.domain.model.UserInfoResponse.UserInfoResponse
import com.example.farmersecom.features.profile.domain.model.editPersonalProfile.EditPersonalInfoEntity
import com.example.farmersecom.features.profile.domain.model.editPersonalProfile.EditPersonalInfoResponse
import com.example.farmersecom.features.profile.domain.usecase.*
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.example.farmersecom.utils.extensionFunctions.handleErros.ErrorBodyExtension.getMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val userImageUseCase: UploadUserImageUseCase,
    private val getUserFullProfileUseCase: GetUserFullProfileUseCase,
    private val deleteAllCartItemsUseCase: DeleteAllCartItemsUseCase,
    private val sharedPrefsHelper:SharedPrefsHelper,
    private val editPersonalInfoUseCase: EditPersonalInfoUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase) :ViewModel()
{


    /// sharing entity for fragment
    var userInfoResponse:UserInfoResponse? =null




    /** Get Profile Use Case*/
    private val _userNetworkEntity:MutableSharedFlow<NetworkResource<ProfileNetworkEntity>>
    = MutableSharedFlow(0)
    val userNetworkEntity:SharedFlow<NetworkResource<ProfileNetworkEntity>> = _userNetworkEntity


    fun getProfile() = viewModelScope.launch(Dispatchers.IO)
    {

        _userNetworkEntity.emit(NetworkResource.Loading())
        try
        {
            val response = getUserProfileUseCase.getUserProfile()
            _userNetworkEntity.emit( handleProfileResponse(response))
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _userNetworkEntity.emit(NetworkResource.Error(e.message))
                else -> _userNetworkEntity.emit(NetworkResource.Error(e.message))
            } // when closed
        } // catch closed
    } //  getProfile closed

    private fun handleProfileResponse(response: Response<ProfileNetworkEntity>): NetworkResource<ProfileNetworkEntity>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }

    /** Upload Image Use Case*/



        private val _uploadUserImgResponse:MutableStateFlow<NetworkResource<ChangePhotoResponse>> = MutableStateFlow(
            NetworkResource.None())
    val uploadUserImgResponse:StateFlow<NetworkResource<ChangePhotoResponse>> = _uploadUserImgResponse


    fun uploadUserImg(file:MultipartBody.Part) = viewModelScope.launch(Dispatchers.IO)
    {
        _uploadUserImgResponse.value = NetworkResource.Loading()
        try
        {
            val response = userImageUseCase.uploadUserImage(file)
            _uploadUserImgResponse.value = handleUploadImageResponse(response)

        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _uploadUserImgResponse.value = NetworkResource.Error("Something went wrong")
                else -> _uploadUserImgResponse.value = NetworkResource.Error("No Internet Connection"+e.message)
            } // when closed
        }


    } //  getProfile closed



    private fun handleUploadImageResponse(response: Response<ChangePhotoResponse>): NetworkResource<ChangePhotoResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 -> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    } // handleUploadImageResponse closed

    /** Get full user profile **/


    private val _userFullProfileResponse:MutableStateFlow<NetworkResource<UserInfoResponse>> = MutableStateFlow(
        NetworkResource.None())
    val userFullProfileResponse:StateFlow<NetworkResource<UserInfoResponse>> = _userFullProfileResponse


    fun getFullUserProfile() = viewModelScope.launch(Dispatchers.IO)
    {
        try
        {
            val response = getUserFullProfileUseCase.getUserFullProfile()
            _userFullProfileResponse.value = handleFullUserProfileResponse(response)
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _userFullProfileResponse.value = NetworkResource.Error("Something went wrong")
                else -> _userFullProfileResponse.value = NetworkResource.Error("No Internet Connection")
            } // when closed
        } // catch closed
    } //  getProfile closed

    private fun handleFullUserProfileResponse(response: Response<UserInfoResponse>): NetworkResource<UserInfoResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 ,404-> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }


    /** Edit Personal info */



    private val _editPersonalInfoResponse:MutableSharedFlow<NetworkResource<EditPersonalInfoResponse>> =
        MutableSharedFlow(0)
    val editPersonalInfoResponse:SharedFlow<NetworkResource<EditPersonalInfoResponse>> = _editPersonalInfoResponse

    fun editPersonalInfoUseCase(personalInfoEntity: EditPersonalInfoEntity) = viewModelScope.launch(Dispatchers.IO)
    {
        _editPersonalInfoResponse.emit(NetworkResource.Loading())
        try
        {
            val response = editPersonalInfoUseCase.editPersonalInfo(personalInfoEntity)
            _editPersonalInfoResponse.emit(handleEditPersonalInfoResponse(response))
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException -> _editPersonalInfoResponse.emit (NetworkResource.Error(e.message))
                else -> _editPersonalInfoResponse.emit(NetworkResource.Error(e.message))
            } // when closed
        } // catch closed
    } //  getProfile closed


    private fun handleEditPersonalInfoResponse(response: Response<EditPersonalInfoResponse>): NetworkResource<EditPersonalInfoResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 ,404-> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    } // handleEditPersonalInfoResponse

    /*****/


    /** Change Password **/


    private var _statusMsgResponse:MutableSharedFlow<NetworkResource<StatusMsgResponse>>
            = MutableSharedFlow(0)
    val statusMsgResponse: SharedFlow<NetworkResource<StatusMsgResponse>>
            = _statusMsgResponse


    fun changePassword(oldPassword:String,newPassword:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _statusMsgResponse.emit(NetworkResource.Loading())
        try
        {
            val response = changePasswordUseCase.changePasswordUseCase(oldPassword,newPassword)
            _statusMsgResponse.emit(handleStatusMessageResponse(response))
        }catch (e:Exception)
        {
            when (e)
            {
                is HttpException ->  _statusMsgResponse.emit(NetworkResource.Error(e.message))
                else ->
                {
                    _statusMsgResponse.emit(NetworkResource.Error(e.message))
                }
            } // when closed
        }
    } //   closed

    private fun handleStatusMessageResponse(response: Response<StatusMsgResponse>): NetworkResource<StatusMsgResponse>
    {
        return when(response.code())
        {
            200,201 -> NetworkResource.Success(response.body())
            400 ,404-> NetworkResource.Error(response.errorBody()?.getMessage())
            else -> NetworkResource.Error("Something went Wrong  + ${response.code()}")
        } // when closed
    }


    fun getAuthToken() : String? = sharedPrefsHelper.getToken()

    fun saveUser(user:User)   = sharedPrefsHelper.saveUser(user)


    fun clearToken()  = sharedPrefsHelper.clearToken()

    fun clearUser() = sharedPrefsHelper.clearUser()


    fun clearCartOnLogout() = viewModelScope.launch(Dispatchers.IO)
    {
        deleteAllCartItemsUseCase.deleteAllCartItem()
    }

    fun clearFiltersOnLogOut() = sharedPrefsHelper.clearFilters()


} // ProfileViewModel closed