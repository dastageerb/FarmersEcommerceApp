package com.example.farmersecom.features.communitySection.data.framework

import com.example.farmersecom.features.communitySection.domain.models.GetPostsResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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
  //  @POST("api/post/add")
    suspend fun editPost
                (
        id:String,
        @Part("title")unit: RequestBody,
        @Part("description")location: RequestBody,
        @Part image: MultipartBody.Part,
    ):Response<StatusMsgResponse>



    @POST("api/post/delete/{id}")
    suspend fun deletePost
                (id:String):Response<StatusMsgResponse>


    @GET("api/post/myPosts")
    suspend fun getPostByUser():Response<GetPostsResponse>








}