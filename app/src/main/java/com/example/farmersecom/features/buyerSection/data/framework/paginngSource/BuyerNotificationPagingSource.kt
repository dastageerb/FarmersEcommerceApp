package com.example.farmersecom.features.buyerSection.data.framework.paginngSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.farmersecom.features.buyerSection.data.framework.BuyerDashboardApi
import com.google.gson.JsonObject
import retrofit2.HttpException
import java.io.IOException

class BuyerNotificationPagingSource(
    private val api: BuyerDashboardApi,

) : PagingSource<Int,JsonObject>()
{

    override fun getRefreshKey(state: PagingState<Int,JsonObject>): Int?
    {
        return state.anchorPosition?.let()
        {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,JsonObject>
    {
        val page = params.key ?: 1

        return try
        {
              val  response = api.getBuyerNotification(page,params.loadSize)

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