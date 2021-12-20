package com.example.farmersecom.features.search.data.frameWork


import com.example.farmersecom.features.search.domain.model.SearchResponse
import com.example.farmersecom.features.search.domain.model.category.GetCategoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi
{

    // TODO

    @GET("api/product")
    suspend fun getSearchedResult(
                @Query("query")query:String,
                @Query("category")category:String,
                @Query("location")location:String) : Response<SearchResponse>


    // TODO

    @GET("api/category/getCategory")
    suspend fun getAllCategories():Response<GetCategoriesResponse>


}