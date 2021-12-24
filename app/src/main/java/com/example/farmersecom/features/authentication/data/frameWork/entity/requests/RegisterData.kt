package com.example.farmersecom.features.authentication.data.frameWork.entity.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterData(


    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "contactNumber")
    val contactNumber: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "dateOfBirth")
    val dateOfBirth: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "address")
    val address: String,
    @Json(name = "postalCode")
    val postalCode: Int,
)