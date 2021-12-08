package com.example.farmersecom.features.storeAdmin.data.framework.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.http.Multipart

@JsonClass(generateAdapter = true)
data class NewProduct(
    @Json(name = "name")
    val productName: String,
    @Json(name = "price")
    val productPrice: Int,
    @Json(name = "description")
    val productDescription: String,
    @Json(name = "category")
    val productCategory: String,
    @Json(name = "unit")
    val productUnit: String,
    @Json(name = "quantity")
    val productQuantity: Int,
    @Json(name = "productPicture")
    val productPicture: String)
{

    @Throws(JSONException::class)
    fun toJson(): JSONObject
    {
        val `object` = JSONObject()
        `object`.put("name", productName)
        `object`.put("price", productPrice)
        `object`.put("description", productDescription)
        `object`.put("category", productCategory)
        `object`.put("unit", productUnit)
        `object`.put("quantity", productQuantity)
        `object`.put("productPicture", productPicture)
        return `object`
    }


}