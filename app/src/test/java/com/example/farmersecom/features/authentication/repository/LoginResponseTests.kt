package com.example.farmersecom.features.authentication.repository


import com.example.farmersecom.ApiAbstract
import com.example.farmersecom.features.authentication.data.frameWork.AuthApi
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.LogInData
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

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
        val loginEntity = LogInData("dastageerg44@gmail.com","12345678")
        val response = authApi.logInViaEmail(loginEntity)
        assertThat(response.body()?.status ,`is`("success"))
        assertThat(response.body()?.message ,`is`("User Successfully Logged In"))

    }

    /// error test cases

    @Test
    fun invalidEmail()  = runBlocking()
    {
        enqueueResponse(directory,"/login_email_invalid.json")
        val loginEntity = LogInData("dastageerg44@.com","12345678")
        val response = authApi.logInViaEmail(loginEntity)
        assertThat(response.body()?.status ,`is`("failed"))
        assertThat(response.body()?.message ,`is`("email is invalid"))
    }


    @Test
    fun incorrectPassword()  = runBlocking()
    {
        enqueueResponse(directory,"/login_password_incorrect.json")
        val loginEntity = LogInData("dastageerg44@gmail.com","12345")
        val response = authApi.logInViaEmail(loginEntity)
        assertThat(response.body()?.status ,`is`("failed"))
        assertThat(response.body()?.message ,`is`("password incorrect"))
    }


    @Test
    fun userNotFound()  = runBlocking()
    {
        enqueueResponse(directory,"/login_user_not_found.json")
        val loginEntity = LogInData("dastageerg@gmail.com","12345")
        val response = authApi.logInViaEmail(loginEntity)
        assertThat(response.body()?.status ,`is`("failed"))
        assertThat(response.body()?.message ,`is`("user not found"))
    }




} // testing