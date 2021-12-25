package com.example.farmersecom.features.profile.data.framework

import com.example.farmersecom.features.profile.data.framework.entities.ProfileNetworkEntity
import com.example.farmersecom.features.profile.domain.EntityMapper
import com.example.farmersecom.features.profile.domain.model.Profile
import javax.inject.Inject

class ProfileNetworkEntityMapper @Inject constructor():EntityMapper<ProfileNetworkEntity,Profile>
{

//    override fun entityToModel(entity: ProfileNetworkEntity): Profile
//    {
//        return Profile(
//            fullName = entity.fullName,
//            isSeller = entity.isSeller,
//            userImgUrl = entity.userImgUrl)
//    }
//
//    override fun modelToEntity(model: Profile): ProfileNetworkEntity
//    {
//        return ProfileNetworkEntity(
//            fullName = model.fullName,
//            isSeller = model.isSeller,
//            userImgUrl = model.userImgUrl)
//    }

}