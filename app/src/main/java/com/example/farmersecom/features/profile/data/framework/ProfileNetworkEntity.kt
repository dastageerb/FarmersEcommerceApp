package com.example.farmersecom.features.profile.data.framework


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileNetworkEntity(
    @Json(name = "address")
    val address: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "contactNumber")
    val contactNumber: String,
    @Json(name = "dateOfBirth")
    val dateOfBirth: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "fullName")
    val fullName: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "postalCode")
    val postalCode: Int,
    @Json(name = "profilePicture")
    val profilePicture: String,
    @Json(name = "province")
    val province: String
)