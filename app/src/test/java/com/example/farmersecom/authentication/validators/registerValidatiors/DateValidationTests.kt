package com.example.farmersecom.authentication.validators.registerValidatiors

import com.google.common.truth.Truth.assertThat
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@RunWith(JUnit4::class)
class DateValidationTests
{

    // error Test cases

    @Test
    fun emptyDate()
    {
        val result = isValidDate("")
        assertThat(result).isFalse()
    }

    @Test
    fun invalidDate()
    {
        val result = isValidDate("2/30/2021")
        assertThat(result).isFalse()
    }

    //  valid Date case

    @Test
    fun validDate()
    {
        val result = isValidDate("31/03/2021")
        assertThat(result).isTrue()
    }



    private fun isValidDate(date: String): Boolean
    {
        if(!date.nonEmpty())
        {

            return false
        }
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE) //
        var testDate: Date
        try
        {
            testDate = sdf.parse(date)
        } catch (e: ParseException)
        {

            return false
        }
        if (!sdf.format(testDate).equals(date))
        {

            return false
        }
        return true
    }  // isValidDate


} // DateValidationTest