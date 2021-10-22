package com.example.farmersecom.features.profile.data.framework

import com.example.farmersecom.features.profile.data.framework.entities.ChangePhotoNetworkEntity
import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.features.profile.domain.EntityMapper
import com.example.farmersecom.features.profile.domain.model.ChangePhotoResponse
import com.example.farmersecom.features.profile.domain.model.Profile
import javax.inject.Inject

class ChangePhotoNetworkEntityMapper @Inject constructor():EntityMapper<ChangePhotoNetworkEntity,ChangePhotoResponse>
{
    override fun entityToModel(entity: ChangePhotoNetworkEntity): ChangePhotoResponse
    {
        return  ChangePhotoResponse(message = entity.message
            , status = entity.status
            , userImgUrl = entity.userImgUrl )
    }

    override fun modelToEntity(model: ChangePhotoResponse): ChangePhotoNetworkEntity
    {
       return  ChangePhotoNetworkEntity(message = model.message
           , status = model.status
           , userImgUrl = model.userImgUrl)
    }


}