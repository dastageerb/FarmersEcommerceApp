package com.example.farmersecom.authentication.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.authentication.data.entity.requests.RegisterEntity
import com.example.farmersecom.authentication.presentation.register.utils.Utils.getList
import com.example.farmersecom.authentication.presentation.register.utils.Utils.setAdapter
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentRegisterBinding
import com.example.farmersecom.utils.ContextExtension.showToast
import com.wajahatkarim3.easyvalidation.core.view_ktx.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() , View.OnClickListener
{

    private val viewModel:RegisterViewModel by viewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentRegisterBinding
    {
        return FragmentRegisterBinding.inflate(layoutInflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegFragRegister.setOnClickListener(this)
        // Go back to Login Fragment on Click
        binding.buttonRegFragLogin.setOnClickListener(this)

            initView()

    } // onViewCreated closed

    private  fun initView() = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
    {

        binding.apply() {
            //disable  autoCompleteCity until province selected
            autoCompleteCity.isEnabled = false
            // set Adapter for province autoCompleteView
            autoCompleteProvince.setAdapter(requireContext(), R.array.Province)

            // set cities when Province is selected (clicked)
            autoCompleteProvince.onItemClickListener = AdapterView.OnItemClickListener() { parent, _, position, _ ->
                // enable autoCompleteCity when province is selected
                autoCompleteCity.isEnabled = true
                when (parent.getItemAtPosition(position))
                {
                    "Sindh" -> autoCompleteCity.setAdapter(requireContext(), R.array.Sindh)
                    "Balochistan" -> autoCompleteCity.setAdapter(requireContext(), R.array.Balochistan)
                    "Punjab" -> autoCompleteCity.setAdapter(requireContext(), R.array.Punjab)
                    "Kpk" -> autoCompleteCity.setAdapter(requireContext(), R.array.KPK)
                } // when closed
            } // autoCompleteProvince OnClickListener
        }
    } /// initView closed


    override fun onClick(v: View?)
    {
        when (v?.id)
        {
            R.id.buttonRegFragRegister -> validateAndRegister()
            // If user Already Has account Click on Login Button to GoBack to Login Screen //
            R.id.buttonRegFragLogin -> findNavController().popBackStack()
        } // when closed
    } // onClick closed



   /**  validate the whole Registration form then Register */
    private fun validateAndRegister() = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
    {
        val fName  = binding.editTextRegisterFragFirstName.text.toString().trim() // firstName
        val lName = binding.editTextRegisterFragLastName.text.toString().trim() // lastName
        val contact = binding.editTextRegisterFragContact.text.toString().trim()
        val email  = binding.editTextRegisterFragEmail.text.toString().trim()
        val pass = binding.editTextRegisterFragPassword.text.toString().trim() // password

        val rePass = binding.editTextRegisterFragReEnterPassword.text.toString().trim() // Re Entered Password

        /// Get Selected Radio Button Id first
        val radioButtonId   = binding.radioGroupRegisterFrag.checkedRadioButtonId
        // then get Gender based on selected button
        val gender = binding.radioGroupRegisterFrag
            .findViewById<RadioButton>(radioButtonId).text.toString()

        val dateOfBirth = binding.editTextRegisterFragDate.text.toString().trim() // date input
        val province = binding.autoCompleteProvince.text.toString().trim()
        val city = binding.autoCompleteCity.text.toString().trim()
        val address = binding.editTextRegisterFragAddress.text.toString().trim()
        val postalCode = binding.editTextRegisterFragPostalCode.text.toString().trim()


        val validPersonalInfo = isPersonalInfoValid(fName,lName,contact,email,pass,rePass)
        val validDob = isValidDate(dateOfBirth)
        val validProvince = isProvinceValid(province)
        val validCity =  isCityValid(city,province)
        val validPostalInfo = isPostalInfoValid(address,postalCode)

        if(validPersonalInfo && validDob && validProvince && validCity && validPostalInfo)
        {
            val registerEntity = RegisterEntity(fName,lName,contact,email,rePass,
                gender,dateOfBirth,province,city,address,postalCode.toInt())
            viewModel.register(registerEntity)
        }

    } // register closed


    /** valid the personal info
     * this method uses a library Easy validation by Sir @Wajah_Karim
     * */

    private fun isPersonalInfoValid(fName: String, lName: String, contact: String, email: String, pass: String, rePass: String):Boolean
    {
        return fName.nonEmpty { binding.editTextRegisterFragFirstName.error = it }
               && lName.nonEmpty { binding.editTextRegisterFragLastName.error = it }
               && contact.validator()
                   .nonEmpty()
                   .minLength(11)
                   .maxLength(11)
                   .addErrorCallback { binding.editTextRegisterFragContact.error = it }
                   .check()
               && email.validEmail{ binding.editTextRegisterFragEmail.error = it }
               && pass.validator()
                   .nonEmpty()
                   .minLength(6)
                   .maxLength(16)
                   .addErrorCallback { binding.editTextRegisterFragPassword.error = it }
                   .check()
               && rePass.contains(pass){ binding.editTextRegisterFragReEnterPassword.error = " Password Does not match "}
    } // isPersonalInfoValid


    /** check the Dob is not empty
     *  check if it a valid date or not
     *  check if has a valid format
     * */

    private fun isValidDate(date: String): Boolean
    {
        if(!date.nonEmpty())
        {
            binding.editTextRegisterFragDate.error = "date is empty "
            return false
        }
        val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE)
        var testDate: Date
        try
        {
            testDate = sdf.parse(date)
        } catch (e: ParseException)
        {
            binding.editTextRegisterFragDate.error = "invalid date format."
            return false
        }
        if (!sdf.format(testDate).equals(date))
        {
            binding.editTextRegisterFragDate.error = "invalid date"
            return false
        }
        return true
    }  // isValidDate


    /** check province  is not empty
     * get list from resource and perform check
     * check the entered values is in the list or no
     * */

    private fun isProvinceValid(province: String): Boolean
    {
        return if(province.isEmpty())
        {
            binding.autoCompleteProvince.error = "Please select a province"
            return false
        }
        // R.array.id.getList(resource) is an extension function that returns a list based on id
        else if(!R.array.Province.getList(resources).contains(province))
        {
            binding.autoCompleteProvince.error = "invalid province"
            false
        }
        else
        {
            binding.autoCompleteProvince.error = null
            true
        }
    } // isProvinceValid




    /** check if city is not empty
     *  selected from a valid province
     */
    private fun isCityValid(city: String, province: String): Boolean
    {
        return if(city.isEmpty())
        {
            binding.autoCompleteCity.error = "Please select a city"
            return false
        }
       else when(province)
        {
            // get province and search the city through its city list
            "Sindh" -> checkCityInProvince(R.array.Sindh.getList(resources),city)
            "Balochistan" -> checkCityInProvince(R.array.Balochistan.getList(resources),city)
            "Punjab" ->checkCityInProvince(R.array.Punjab.getList(resources),city)
            "Kpk" -> checkCityInProvince(R.array.KPK.getList(resources),city)
           else ->
           {
               binding.autoCompleteCity.error = "Invalid city"
               false
           }
       } /// when closed
    } // isCity valid


    /** checkCityInProvince takes a list of cities
     * and check wether the entered(input) city is from the list or not */

    private fun checkCityInProvince(list: Array<String>, city: String): Boolean
    {
        return if(!list.contains(city))
        {
            binding.autoCompleteCity.error = "City does not belongs to this province"
            false
        }else
        {
            binding.autoCompleteCity.error = null
            true
        }

    } // checkCityInProvince





    /** validate the postal info (address , postal code)
     * validated with Easy validation by Sir @Wajah_Karim
     * */

    private fun isPostalInfoValid(address: String, postalCode: String):Boolean
    {
        return address.nonEmpty { binding.editTextRegisterFragAddress.error = it }
               && postalCode.validator()
                   .nonEmpty()
                   .minLength(5)
                   .maxLength(5)
                   .addErrorCallback { binding.editTextRegisterFragPostalCode.error = it }
                   .check()
    } // isPostalInfoValid





} // Register Fragment closed



