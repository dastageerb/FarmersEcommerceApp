package com.example.farmersecom.features.cart.data.business

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.farmersecom.features.buyerSection.data.framework.paginngSource.BuyerFavouritesPagingSource
import com.example.farmersecom.features.cart.data.framework.CartApi
import com.example.farmersecom.features.cart.data.framework.pagingSource.CartPagingSource
import com.example.farmersecom.features.cart.domain.CartRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(private val cartApi: CartApi):CartRepository
{

    override suspend fun getCartItems(): Flow<PagingData<JsonObject>>
    = Pager(PagingConfig(20)) { CartPagingSource(cartApi) }.flow



}