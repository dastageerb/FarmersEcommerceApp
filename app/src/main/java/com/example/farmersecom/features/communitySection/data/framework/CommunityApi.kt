package com.example.farmersecom.features.communitySection.data.framework

import androidx.room.Delete
import com.example.farmersecom.features.communitySection.domain.models.GetPostsResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CommunityApi
{


    @Multipart
    @POST("api/post/add")
    suspend fun addPost
                (@Part("title")unit: RequestBody,
                @Part("description")location: RequestBody,
                @Part image: MultipartBody.Part,
    ):Response<StatusMsgResponse>


    @Multipart
   @POST("api/post/edit/{id}")
    suspend fun editPost
                (
        @Path("id")id:String,
        @Part("title")tile: RequestBody,
        @Part("description")description: RequestBody,
        @Part image: MultipartBody.Part?=null,
    ):Response<StatusMsgResponse>



    @DELETE("api/post/delete/{id}")
    suspend fun deletePost
                (@Path("id")id:String):Response<StatusMsgResponse>


    @GET("api/post/myPosts")
    suspend fun getPostByUser():Response<GetPostsResponse>








}