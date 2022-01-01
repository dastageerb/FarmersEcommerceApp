package com.example.farmersecom.features.communitySection.domain

import com.example.farmersecom.features.communitySection.domain.models.GetPostsResponse
import com.example.farmersecom.features.communitySection.domain.models.Post
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface CommunityRepository
{

    suspend fun getAllPosts():Response<GetPostsResponse>

    suspend fun getSearchedPosts(query:String):Response<GetPostsResponse>

    suspend fun getPostById(postId:String) : Response<Post>




    suspend fun getPostsByUser():Response<GetPostsResponse>


    suspend fun addPost(title:String,description:String,image:MultipartBody.Part):Response<StatusMsgResponse>

    suspend fun updatePost(postId:String,title:String,description:String,image:MultipartBody.Part):Response<StatusMsgResponse>

    suspend fun deletePost(postId:String):Response<StatusMsgResponse>


}