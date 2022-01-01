package com.example.farmersecom.features.communitySection.domain.usecases

import com.example.farmersecom.features.communitySection.domain.CommunityRepository
import com.example.farmersecom.features.storeAdmin.data.framework.entities.requests.NewProduct
import com.example.farmersecom.features.storeAdmin.domain.StoreAdminRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(private val communityRepository: CommunityRepository)
{

    suspend fun deletePost(postId:String,title:String,description:String,image: MultipartBody.Part)
    = communityRepository.deletePost(postId)

} // addProductUseCase closed