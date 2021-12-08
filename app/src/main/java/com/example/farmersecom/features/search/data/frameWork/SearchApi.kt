package com.example.farmersecom.features.search.data.frameWork


import com.example.farmersecom.features.search.domain.model.SearchItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi
{

    // TODO

    @GET("api/")
    suspend fun getSearchedResult(
                @Query("query")query:String,
                @Query("productCategory")category:String,
                @Query("productLocation")location:String,
                @Query("pageNo")pageNo:Int,
                @Query("PageSize")pageSize:Int,
    ) : Response<List<SearchItem>>


}