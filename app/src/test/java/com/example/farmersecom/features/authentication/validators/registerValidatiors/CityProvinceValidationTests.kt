package com.example.farmersecom.features.authentication.validators.registerValidatiors

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CityProvinceValidationTests
{

//    private val provinces = arrayOf("Sindh","Punjab","Balochistan","KPK")
//
//    private val sindhCitiesList = arrayOf("KNShah","Hyderabad","Jamshoro","Dadu")
//    private val punjabCitiesList = arrayOf("Sargodha","Rawalpindhi","Faisalabad","Lahore")
//    private val balochistanCitiesList = arrayOf("Chamman","Khuzdar","Quetta","Pajgur")
//    private val kpKCitiesList = arrayOf("Peshawar","Mardan","Kohat","Abbottabad")
//
//

//
//    // error test cases for province
//
//    @Test
//    fun provinceEmpty()
//    {
//        val result = isProvinceValid("")
//        assertThat(result).isFalse()
//    } // province Empty
//
//    @Test
//    fun invalidProvince()
//    {
//        val result = isProvinceValid("Pakistan")
//        assertThat(result).isFalse()
//    }
//
//    // valid test case
//
//    @Test
//    fun `province is valid`()
//    {
//        val result = isProvinceValid("Sindh")
//        assertThat(result).isTrue()
//    }
//
//    /** Province Validation*/
//
//    private fun isProvinceValid(province: String): Boolean
//    {
//        return province.isNotEmpty() && provinces.contains(province)
//    } // isProvinceValid
//
//
//    /** Error test cases for city */
//
//
//
//    @Test
//    fun emptyCity()
//    {
//        val result = isCityValid("","Sindh")
//        assertThat(result).isFalse()
//    } // invalidCity
//
//    @Test
//    fun invalidCity()
//    {
//        val result = isCityValid("Kohat","Sindh")
//        assertThat(result).isFalse()
//    } // invalidCity
//
//    // valid test case for city
//    @Test
//    fun validCity()
//    {
//        val result = isCityValid("Dadu","Sindh")
//        assertThat(result).isTrue()
//    }
//
//
////    private fun isCityValid(city: String, province: String): Boolean
////    {
////        // province will not be empty because it is validated first so no need to check below
////        return if(city.isEmpty())
////        {
////            return false
////        }h" -> checkCityInProvince(sindhCitiesList,city)
//////            "Balochistan" -> checkCityInProvince(balochistanCitiesList,city)
//////            "Punjab" ->checkCityInProvince(punjabCitiesList,city)
//////            "Kpk" -> checkCityInProvince(kpKCitiesList,city)
//////            else ->
//////            {
//////                false
//////            }
//////        } /// when closed
//////    } // isCity valid
////
////
////    /** checkCityInProvince takes a list of cities
////     * and check wether the entered(input) city is from the list or not */
////
////    private fun checkCityInProvince(list: Array<String>, city: String): Boolean
////    {
////        return list.contains(city)
////    } // checkCityInProvince
////        else when(province)
////        {
////            // get province and search the city through its city list
////            "Sind


} // CityProvinceValidation Tests