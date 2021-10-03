package com.example.farmersecom.features.authentication.repository


import com.example.farmersecom.ApiAbstract
import com.example.farmersecom.features.authentication.data.frameWork.AuthApi
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RegisterResponseTests : ApiAbstract<AuthApi>()
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
        val registerEntity = RegisterData("shoaib","bughio","03063255130"
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
        val registerEntity = RegisterData("shoaib","bughio","03063255130"
            ,"dastageerg44@gmail.com","123456","Male",
            "22/4/2021","Sindh","K.N.Shah","Shahbaz Colony","76260".toInt())

        val response = authApi.registerViaEmail(registerEntity)
        assertThat(response.body()?.status ,`is`("failed"))
        assertThat(response.body()?.message, `is`("Email Already Exists"))
    }




} // testing