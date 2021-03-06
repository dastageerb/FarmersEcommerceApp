package com.example.farmersecom.features.authentication.data.frameWork.entity.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "address")
    var address: String?,
    @Json(name = "city")
    var city: String?,
    @Json(name = "contactNumber")
    var contactNumber: String?,
    @Json(name = "dateOfBirth")
    var dateOfBirth: String?,
    @Json(name = "email")
    var email: String?,
    @Json(name = "fullName")
    var fullName: String?,
    @Json(name = "gender")
    var gender: String?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "isSeller")
    var isSeller: Boolean?,
    @Json(name = "postalCode")
    var postalCode: Int?,
    @Json(name = "profilePicture")
    var profilePicture: String?
)