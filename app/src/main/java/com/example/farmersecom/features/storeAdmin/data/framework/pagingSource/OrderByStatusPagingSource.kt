package com.example.farmersecom.features.storeAdmin.data.framework.pagingSource

//class OrderByStatusPagingSource(private val orderStatus:String,
//                                private val api:StoreAdminApi,
//) : PagingSource<Int,OrderStatus>()
//{
//
//    override fun getRefreshKey(state: PagingState<Int,OrderStatus>): Int?
//    {
//        return state.anchorPosition?.let()
//        {
//            val anchorPage = state.closestPageToPosition(it)
//            anchorPage?.prevKey?.plus(1) ?:anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,OrderStatus>
//    {
//        val page = params.key ?: 1
//
//        return try
//        {
//              val  response = api.getOrderByStatus(page,params.loadSize,orderStatus)
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