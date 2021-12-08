package com.example.farmersecom.features.productDetails.domain.useCase

import com.example.farmersecom.features.productDetails.domain.ProductDetailsRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val productDetailsRepository: ProductDetailsRepository)
{

    suspend fun getProductById(id:String) = productDetailsRepository.getProductById(id)


} // GetProductsByIdUseCase