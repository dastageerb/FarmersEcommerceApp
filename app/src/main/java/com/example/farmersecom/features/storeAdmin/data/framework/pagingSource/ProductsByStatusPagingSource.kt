package com.example.farmersecom.features.storeAdmin.data.framework.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.farmersecom.features.storeAdmin.data.framework.StoreAdminApi
import com.example.farmersecom.features.storeAdmin.domain.model.productStatusResponse.ProductStatus
import retrofit2.HttpException
import java.io.IOException

//class ProductsByStatusPagingSource(private val statusIsActive:Boolean,
//    private val api:StoreAdminApi,
//) : PagingSource<Int, ProductStatus>()
//{
//
//    override fun getRefreshKey(state: PagingState<Int, ProductStatus>): Int?
//    {
//        return state.anchorPosition?.let()
//        {
//            val anchorPage = state.closestPageToPosition(it)
//            anchorPage?.prevKey?.plus(1) ?:anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductStatus>
//    {
//        val page = params.key ?: 1
//
//        return try
//        {
//              val  response = api.getProductByStatus(page,params.loadSize,statusIsActive)
//
//            LoadResult.Page(
//                data = response.body()!!,
//                prevKey = if(page==1) null else page-1,
//                nextKey = if(response.body().isNullOrEmpty()) null else page+1
//            )
//        }catch (e: IOException)
//        {
//            LoadResult.Error(e)
//        } catch (e: HttpException)
//        {
//            LoadResult.Error(e)
//        }
//
//    } // load closed
//
//
//} // UnSplashPagingSource closed