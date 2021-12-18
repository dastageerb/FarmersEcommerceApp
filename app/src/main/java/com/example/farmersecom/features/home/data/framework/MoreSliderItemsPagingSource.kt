package com.example.farmersecom.features.home.data.framework

import android.nfc.Tag
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.farmersecom.features.home.domain.model.MoreProductsResponseItem
import com.example.farmersecom.features.home.domain.model.more.MoreProduct
import com.example.farmersecom.utils.constants.Constants.TAG
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class MoreSliderItemsPagingSource(
    private val api: HomeApi,
    private val query:String,
) : PagingSource<Int,MoreProduct>()
{

    override fun getRefreshKey(state: PagingState<Int, MoreProduct>): Int?
    {
        return state.anchorPosition?.let()
        {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,MoreProduct>
    {
        val page = params.key ?: 1

        return try
        {
            val  response = api.getMoreSliderItems(query,page,params.loadSize)
            Timber.tag(TAG).d("response:-> ${response.body()?.products}")

            LoadResult.Page(
                data = response.body()?.products!!,
                prevKey = if(page==1) null else page-1,
                nextKey = if(response.body()!!.products.isNullOrEmpty()) null else page+1)
        }catch (e: IOException)
        {
            LoadResult.Error(e)
        } catch (e: HttpException)
        {
            LoadResult.Error(e)
        }

    } // load closed


} // UnSplashPagingSource closed
