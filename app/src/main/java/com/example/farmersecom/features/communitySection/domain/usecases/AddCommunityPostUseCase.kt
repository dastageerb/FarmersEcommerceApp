package com.example.farmersecom.features.communitySection.domain.usecases

import com.example.farmersecom.features.communitySection.domain.CommunityRepository
import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class AddCommunityPostUseCase @Inject constructor(private val communityRepository: CommunityRepository)
{

    suspend fun addCommunityPost(title:String,description:String,image: MultipartBody.Part)
    = communityRepository.addPost(title,description,image)

} // addProductUseCase closed