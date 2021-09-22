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
class RegisterResponseTests : ApiAbstract<AuthApi>()
{

    lateinit var authApi: AuthApi
    val directory = "auth-response"

    @Before
    fun setup()
    {
        authApi = createService(AuthApi::class.java)
    }





} // testing