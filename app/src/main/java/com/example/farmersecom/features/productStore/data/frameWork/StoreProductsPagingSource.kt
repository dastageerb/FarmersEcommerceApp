package com.example.farmersecom.features.productStore.data.frameWork

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.farmersecom.features.productStore.domain.model.Product
import com.example.farmersecom.features.search.data.frameWork.SearchApi
import com.example.farmersecom.features.search.domain.model.SearchItem
import com.google.gson.JsonObject
import retrofit2.HttpException
import java.io.IOException

class StoreProductsPagingSource(
    private val api: ProductStoreApi,
    private val storeId:String,
) : PagingSource<Int,Product>()
{

    override fun getRefreshKey(state: PagingState<Int,Product>): Int?
    {
        return state.anchorPosition?.let()
        {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,Product>
    {
        val page = params.key ?: 1

        return try
        {
              val  response = api.getStoreProductsByStoreId(storeId,page,params.loadSize)

            LoadResult.Page(
                data = response.body()!!,
                prevKey = if(page==1) null else page-1,
                nextKey = if(response.body().isNullOrEmpty()) null else page+1
            )
        }catch (e: IOException)
        {
            LoadResult.Error(e)
        } catch (e: HttpException)
        {
            LoadResult.Error(e)
        }

    } // load closed


} // UnSplashPagingSource closed