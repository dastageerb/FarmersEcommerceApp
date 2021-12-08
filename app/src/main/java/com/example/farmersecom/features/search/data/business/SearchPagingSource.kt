package com.example.farmersecom.features.search.data.business

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.farmersecom.features.search.data.frameWork.SearchApi
import com.example.farmersecom.features.search.domain.model.SearchItem
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    private val api: SearchApi,
    private val query:String,
    private val category:String,
    private val location:String
) : PagingSource<Int,SearchItem>()
{

    override fun getRefreshKey(state: PagingState<Int,SearchItem>): Int?
    {
        return state.anchorPosition?.let()
        {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,SearchItem>
    {
        val page = params.key ?: 1

        return try
        {
              val  response = api.getSearchedResult(query,category,location,page,params.loadSize)

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