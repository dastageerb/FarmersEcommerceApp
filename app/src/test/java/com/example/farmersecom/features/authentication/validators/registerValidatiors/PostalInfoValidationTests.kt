package com.example.farmersecom.features.authentication.validators.registerValidatiors

import com.google.common.truth.Truth.assertThat
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import org.junit.Test

class PostalInfoValidationTests
{

    /// error test cases

    @Test
    fun emptyInfo()
    {
        val value = isPostalInfoValid("","")
        assertThat(value).isFalse()
    }

    // postal code length must be 5
    @Test fun `postal code not valid`()
    {
        val value = isPostalInfoValid("Shahbaz Colony ","123")
        assertThat(value).isFalse()
    }

    // valid test case

    // postal code length must be 5
    @Test fun `valid postal info`()
    {
        val value = isPostalInfoValid("Shahbaz Colony ","76260")
        assertThat(value).isTrue()
    }


    private fun isPostalInfoValid(address: String, postalCode: String):Boolean
    {
        return address.nonEmpty()
               && postalCode.validator()
                   .nonEmpty()
                   .minLength(5)
                   .maxLength(5)
                   .check()
    } // isPostalInfoValid






}