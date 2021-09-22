package com.example.farmersecom.authentication.repository


import com.example.farmersecom.ApiAbstract
import com.example.farmersecom.authentication.data.AuthApi
import com.example.farmersecom.authentication.data.entity.requests.RegisterEntity
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class LoginResponseTests : ApiAbstract<AuthApi>()
{

    lateinit var authApi: AuthApi
    val directory = "auth-response"

    @Before
    fun setup()
    {
        authApi = createService(AuthApi::class.java)
    }

    @Test
    fun registerUserSuccess()  = runBlocking()
    {
        enqueueResponse(directory,"/register_response.json")
        val registerEntity = RegisterEntity("shoaib","bughio","03063255130"
            ,"dastageerg44@gmail.com","123456","Male",
            "22/4/2021","Sindh","K.N.Shah","Shahbaz Colony","76260".toInt())

        val response = authApi.registerViaEmail(registerEntity)
        assertThat(response.body()?.status ,`is`("success"))
        assertThat(response.body()?.message ,`is`("User Registered Successfully"))

    }


    @Test
    fun registerUserFailed()  = runBlocking()
    {
        enqueueResponse(directory,"/register_failed_response.json")
        val registerEntity = RegisterEntity("shoaib","bughio","03063255130"
            ,"dastageerg44@gmail.com","123456","Male",
            "22/4/2021","Sindh","K.N.Shah","Shahbaz Colony","76260".toInt())

        val response = authApi.registerViaEmail(registerEntity)
        assertThat(response.body()?.status ,`is`("failed"))
        assertThat(response.body()?.message, `is`("Email Already Exists"))
    }




} // testing