package com.example.farmersecom.features.communitySection.domain.usecases

import com.example.farmersecom.features.communitySection.domain.CommunityRepository
import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class GetAllCommunityPostsUseCase @Inject constructor(private val communityRepository: CommunityRepository)
{

    suspend fun getAllCommunityPost() = communityRepository.getAllPosts()

} // addProductUseCase closed