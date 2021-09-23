package com.example.farmersecom.authentication.repository


import com.example.farmersecom.ApiAbstract
import com.example.farmersecom.authentication.data.AuthApi
import com.example.farmersecom.authentication.data.entity.requests.LogInEntity
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

     // success test case
    @Test
    fun loginUserSuccess()  = runBlocking()
    {
        enqueueResponse(directory,"/login_success.json")
        val loginEntity = LogInEntity("dastageerg44@gmail.com","12345678")
        val response = authApi.logInViaEmail(loginEntity)
        assertThat(response.body()?.status ,`is`("success"))
        assertThat(response.body()?.message ,`is`("User Successfully Logged In"))

    }

    /// error test cases

    @Test
    fun invalidEmail()  = runBlocking()
    {
        enqueueResponse(directory,"/login_email_invalid.json")
        val loginEntity = LogInEntity("dastageerg44@.com","12345678")
        val response = authApi.logInViaEmail(loginEntity)
        assertThat(response.body()?.status ,`is`("failed"))
        assertThat(response.body()?.message ,`is`("email is invalid"))
    }


    @Test
    fun incorrectPassword()  = runBlocking()
    {
        enqueueResponse(directory,"/login_password_incorrect.json")
        val loginEntity = LogInEntity("dastageerg44@gmail.com","12345")
        val response = authApi.logInViaEmail(loginEntity)
        assertThat(response.body()?.status ,`is`("failed"))
        assertThat(response.body()?.message ,`is`("password incorrect"))
    }


    @Test
    fun userNotFound()  = runBlocking()
    {
        enqueueResponse(directory,"/login_user_not_found.json")
        val loginEntity = LogInEntity("dastageerg@gmail.com","12345")
        val response = authApi.logInViaEmail(loginEntity)
        assertThat(response.body()?.status ,`is`("failed"))
        assertThat(response.body()?.message ,`is`("user not found"))
    }




} // testing