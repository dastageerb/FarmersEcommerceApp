package com.example.farmersecom.features.communitySection.data.business

import com.example.farmersecom.features.communitySection.data.framework.CommunityApi
import com.example.farmersecom.features.communitySection.domain.CommunityRepository
import com.example.farmersecom.features.communitySection.domain.models.GetPostsResponse
import com.example.farmersecom.features.communitySection.domain.models.Post
import com.example.farmersecom.features.home.data.framework.HomeApi
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class CommunityRepositoryImpl(private val communityApi: CommunityApi,private val homeApi: HomeApi)
    : CommunityRepository
{

    override suspend fun getAllPosts(): Response<GetPostsResponse>
    = homeApi.getAllPosts()

    override suspend fun getSearchedPosts(query: String): Response<GetPostsResponse>
    =homeApi.getSearchedPosts(query)

    override suspend fun getPostById(postId: String): Response<Post>
    =homeApi.getPostById(postId)


    override suspend fun getPostsByUser(): Response<GetPostsResponse>
    = communityApi.getPostByUser()

    override suspend fun addPost(title: String, description: String, image: MultipartBody.Part): Response<StatusMsgResponse>
    {
        val titleReqBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionReqBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
       return communityApi.addPost(titleReqBody,descriptionReqBody,image)
    }



    override suspend fun updatePost(postId: String, title: String, description: String, image: MultipartBody.Part?): Response<StatusMsgResponse>
    {
        val titleReqBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionReqBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        return communityApi.editPost(postId,titleReqBody,descriptionReqBody,image)
    } //

    override suspend fun deletePost(postId: String): Response<StatusMsgResponse>
    =   communityApi.deletePost(postId)



}