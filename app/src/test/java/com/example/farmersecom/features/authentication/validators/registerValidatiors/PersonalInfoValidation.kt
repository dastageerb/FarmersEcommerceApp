package com.example.farmersecom.features.authentication.validators.registerValidatiors

import com.google.common.truth.Truth.assertThat
import com.wajahatkarim3.easyvalidation.core.view_ktx.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PersonalInfoValidation
{

    // Error causes in Registration form

    @Test
    fun emptyFields()
    {
        val result = isPersonalInfoValid("","","","","","")
        assertThat(result).isFalse()
    } // emptyFields

    // length of number must be 11
    @Test
    fun invalidNumber()
    {
        val result = isPersonalInfoValid("shoaib","bughio","0306"
            ,"dastageerg44@gmail.com","12345678910","12345678910")
        assertThat(result).isFalse()
    } // invalidNumber



    @Test
    fun invalidEmail()
    {
        val result = isPersonalInfoValid("shoaib","bughio","03063255130"
            ,"dastageerg44@gmail","123456789","123456789")
        assertThat(result).isFalse()
    } // invalidEmail

    @Test
    fun shortPassword()
    {
        val result = isPersonalInfoValid("shoaib","bughio","03063255130"
            ,"dastageerg44@gmail.com","1234567","1234567")
        assertThat(result).isFalse()
    } //  shortPassword



    @Test
    fun passwordDoesNotMatch()
    {
        val result = isPersonalInfoValid("shoaib","bughio","03063255130"
            ,"dastageerg44@gmail.com","12345678","123456789")
        assertThat(result).isFalse()
    } // passwordDoesNotMatch

    // no error is form
    @Test
    fun validatedData()
    {
        val result = isPersonalInfoValid("shoaib","bughio","03063255130"
            ,"dastageerg44@gmail.com","123456789","123456789")
        assertThat(result).isTrue()
    } // passwordDoesNotMatch



    /** Personal Info along with email and Pass */
        private fun isPersonalInfoValid(fName: String, lName: String, contact:
            String, email: String, pass: String, rePass: String):Boolean
        {
            return fName.nonEmpty()
                   && lName.nonEmpty ()
                   && contact.validator()
                       .nonEmpty()
                       .minLength(11)
                       .maxLength(11)
                       .check()
                   && email.validEmail()
                   && pass.validator()
                       .nonEmpty()
                       .minLength(8)
                       .maxLength(16)
                       .check()
                   && rePass.textEqualTo(pass)
        } // isPersonalInfoValid




} // PersonalInfoValidation