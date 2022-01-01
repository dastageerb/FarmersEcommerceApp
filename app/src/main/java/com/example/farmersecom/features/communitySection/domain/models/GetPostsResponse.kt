package com.example.farmersecom.features.communitySection.domain.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetPostsResponse(
    @Json(name = "posts")
    var posts: List<Post>?
)