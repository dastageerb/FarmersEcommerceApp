package com.example.farmersecom.authentication.validators

import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

class ValidatorsTests
{

    /** Login Validators */

    private fun validation(email:String,password: String): Boolean
    {
        return email.validator().
        validEmail().
        nonEmpty().check() &&
               password.nonEmpty()
    } //



}