package com.example.farmersecom.features.search.domain.useCases

import com.example.farmersecom.features.search.domain.SearchRepository
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import javax.inject.Inject

class GetAllCategories @Inject constructor(private val searchRepository: SearchRepository)
{

    suspend fun getAllCategories() = searchRepository.getAllCategories()

} // addProductUseCase closed