package com.example.farmersecom.features.productStore.data.frameWork

//class StoreProductsPagingSource(
//    private val api: ProductStoreApi,
//    private val storeId:String,
//) : PagingSource<Int, Product>()
//{
//
//    override fun getRefreshKey(state: PagingState<Int,Product>): Int?
//    {
//        return state.anchorPosition?.let()
//        {
//            val anchorPage = state.closestPageToPosition(it)
//            anchorPage?.prevKey?.plus(1) ?:anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,Product>
//    {
//        val page = params.key ?: 1
//
//        return try
//        {
//            //  val  response = api.getStoreProductsByStoreId(storeId,page,params.loadSize)
//
////            LoadResult.Page(
//            //    data = response.body()!!,
//            //    prevKey = if(page==1) null else page-1,
//             //   nextKey = if(response.body().isNullOrEmpty()) null else page+1
//           // )
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