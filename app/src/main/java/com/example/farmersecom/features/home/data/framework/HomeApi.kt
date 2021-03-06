package com.example.farmersecom.features.home.data.framework

import com.example.farmersecom.features.communitySection.domain.models.GetPostsResponse
import com.example.farmersecom.features.communitySection.domain.models.Post
import com.example.farmersecom.features.home.domain.model.MoreProductsResponseItem
import com.example.farmersecom.features.home.domain.model.homeModels.HomeLatestItem
import com.example.farmersecom.features.home.domain.model.more.MoreResponseItem
import com.example.farmersecom.features.home.domain.model.sliderModels.HomeSlider
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi
{


    // TODO
    @GET("api/slide/show")
    suspend fun getSliderItems():Response<List<HomeSlider>>

    @GET("api/category/products")
    suspend fun getLatestProducts():Response<List<HomeLatestItem>>


    @GET("api/product")
    suspend fun getMoreSliderItems(
        @Query("query")query:String,
        @Query("page")page:Int,
        @Query("size")size:Int):Response<MoreResponseItem>

    @GET("api/product")
    suspend fun getMoreCategoryItems(
        @Query("category")categoryId:String,
        @Query("page")page:Int,
        @Query("size")size:Int):Response<MoreResponseItem>






    // Community Sections unAuthorizedApis

    @GET("api/post/getAllPosts")
     suspend fun getAllPosts(): Response<GetPostsResponse>

    @GET("api/post/search")
     suspend fun getSearchedPosts(@Query("query")query: String): Response<GetPostsResponse>


     @GET("api/post/get/{id}")
     suspend fun getPostById(@Path("id")id: String): Response<Post>


}