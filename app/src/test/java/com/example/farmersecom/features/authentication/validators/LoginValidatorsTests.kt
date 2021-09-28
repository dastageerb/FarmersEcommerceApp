package com.example.farmersecom.features.authentication.validators

import com.google.common.truth.Truth.assertThat
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginValidatorsTests
{

    /** Login Validators */

    @Test
    fun emailPasswordOk()
    {
        val result = validation("dastageerg44@gmail.com","hello123")
        assertThat(result).isTrue()
    }

    @Test
    fun  checkInvalidEmail()
    {
        val result = validation("dastageerg","hello123")
        assertThat(result).isFalse()
    }

    @Test
    fun  emptyEmail()
    {
        val result = validation("","hello123")
        assertThat(result).isFalse()
    }


    @Test
    fun  emptyPassword()
    {
        val result = validation("dastageerg44@gmail.com","")
        assertThat(result).isFalse()
    }

    @Test
    fun  emptyEmailPassword()
    {
        val result = validation("","")
        assertThat(result).isFalse()
    }

    //// same method in Login Fragment which validates creds
    private fun validation(email:String,password: String): Boolean
    {
        return email.validator().
        validEmail().
        nonEmpty().check() &&
               password.nonEmpty()
    } //



}