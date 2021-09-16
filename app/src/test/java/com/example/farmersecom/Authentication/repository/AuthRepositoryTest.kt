package com.example.farmersecom.Authentication.repository


import com.example.farmersecom.Authentication.data.AuthApi
import com.example.farmersecom.Authentication.data.entity.requests.RegisterEntity
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
class AuthRepositoryTest
{


//    @Rule
//    @JvmField
//    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var api:AuthApi

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun funRegisterUser() = runBlocking()
    {
        enqueueResponse("register_response.json")
        val response = api.registerViaEmail(RegisterEntity("","","",""))
        assertThat(response.body()?.status, `is`("success"))
    }

//
//    @Test
//    fun getPostsTest() = runBlocking {
//        enqueueResponse("posts.json")
//        val posts = service.getPosts().body()
//
//        assertThat(posts, notNullValue())
//        assertThat(posts!!.size, `is`(2))
//        assertThat(posts[0].title, `is`("Title 1"))
//    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap())
    {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers)
        {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }




} // testing