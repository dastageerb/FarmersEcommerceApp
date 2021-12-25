package com.example.farmersecom.features.profile.domain.model.editPersonalProfile


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EditPersonalInfoEntity(
    @Json(name = "address")
    var address: String?,
    @Json(name = "city")
    var city: String?,
    @Json(name = "contactNumber")
    var contactNumber: String?,
    @Json(name = "dateOfBirth")
    var dateOfBirth: String?,
    @Json(name = "firstName")
    var firstName: String?,
    @Json(name = "gender")
    var gender: String?,
    @Json(name = "lastName")
    var lastName: String?,
    @Json(name = "postalCode")
    var postalCode: Int?
)