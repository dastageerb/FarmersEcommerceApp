package com.example.farmersecom.features.buyerSection.data.business

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.farmersecom.features.buyerSection.data.framework.BuyerDashboardApi
import com.example.farmersecom.features.buyerSection.data.framework.paginngSource.BuyerFavouritesPagingSource
import com.example.farmersecom.features.buyerSection.data.framework.paginngSource.BuyerNotificationPagingSource
import com.example.farmersecom.features.buyerSection.domain.BuyerDashboardRepository
import com.example.farmersecom.features.buyerSection.domain.model.orderDetails.OrderDetailsResponse
import com.example.farmersecom.features.storeAdmin.domain.model.StatusMsgResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class BuyerDashBoardRepositoryImpl(private val buyerDashboardApi: BuyerDashboardApi):BuyerDashboardRepository
{

    override suspend fun getOrdersByStatus(orderStatus:Boolean)
    = buyerDashboardApi.getOrdersByStatus(orderStatus)


    override suspend fun getOrderDetailsById(orderId:String): Response<OrderDetailsResponse>
    = buyerDashboardApi.getOrderDetailsById(orderId)


//= Pager(PagingConfig(20)) { BuyerOrderByStatusPagingSource(buyerDashboardApi,orderStatus) }.flow


    override suspend fun getBuyerNotifications(): Flow<PagingData<JsonObject>>
            = Pager(PagingConfig(20)) { BuyerNotificationPagingSource(buyerDashboardApi) }.flow

    override suspend fun getBuyerFavourites(): Flow<PagingData<JsonObject>>
            = Pager(PagingConfig(20)) { BuyerFavouritesPagingSource(buyerDashboardApi) }.flow


    override suspend fun rateProduct(productId: String, rating: Float): Response<StatusMsgResponse>
     = buyerDashboardApi.rateProduct(productId,rating)

    override suspend fun cancelOrder(orderId: String): Response<StatusMsgResponse>
     = buyerDashboardApi.cancelOrder(orderId)


    override suspend fun addProductToFavourites(productId: String):Response<StatusMsgResponse>
     = buyerDashboardApi.addProductToFavourites(productId)

    override suspend fun removeProductFromFavourites(productId: String):Response<StatusMsgResponse>
            = buyerDashboardApi.removeProductFromFavourites(productId)


} // BuyerDashBoardRepositoryImpl