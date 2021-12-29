package com.example.farmersecom.features.authentication.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.R
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentRegisterBinding
import com.example.farmersecom.features.authentication.presentation.register.utils.Utils.setUpAdapter
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.wajahatkarim3.easyvalidation.core.view_ktx.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
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

        subscribeRegisterResponse();

    } // onViewCreated closed

    private fun subscribeRegisterResponse()
    {
        viewLifecycleOwner.lifecycleScope.launch()
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.registerResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.progressBarRegisterFrag.show()
                            Timber.tag(Constants.TAG).d("Loading")
                        }
                        is NetworkResource.Error ->
                        {
                            binding.progressBarRegisterFrag.hide()
                            requireContext().showToast(it.msg.toString())
                            Timber.tag(Constants.TAG).d(it.msg)
                        }
                        is NetworkResource.Success ->
                        {
                            binding.progressBarRegisterFrag.hide()
                            requireContext().showToast(it.data?.message.toString())
                            Timber.tag(Constants.TAG).d(it.data.toString())
                            findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
                        }
                        else -> {}
                    } /// when closed
                } // collect closed
            } // repeatOnLifeCycle closed
        } // lifeCycleScope closed
    } // subscribeRegisterResponse  closed


    private  fun initView() = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
    {

        binding.apply {
            //disable  autoCompleteCity until province selected
            autoCompleteCity.setUpAdapter(requireContext(), R.array.Sindh)


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
        val city = binding.autoCompleteCity.text.toString().trim()
        val address = binding.editTextRegisterFragAddress.text.toString().trim()
        val postalCode = binding.editTextRegisterFragPostalCode.text.toString().trim()


        val validPersonalInfo = isPersonalInfoValid(fName,lName,contact,email,pass,rePass)
        val validDob = isValidDate(dateOfBirth)
        val validCity =  isCityValid(city)
        val validPostalInfo = isPostalInfoValid(address,postalCode)

        if(validPersonalInfo && validDob &&   validCity && validPostalInfo)
        {
            val registerEntity = RegisterData(fName,lName,contactNumber = contact,email = email,rePass,
                gender,dateOfBirth,city,address,postalCode.toInt())
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
                   .minLength(8)
                   .maxLength(16)
                   .addErrorCallback { binding.editTextRegisterFragPassword.error = it }
                   .check()
               && rePass.textEqualTo(pass){ binding.editTextRegisterFragReEnterPassword.error = " Password Does not match "}
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







    /** check if city is not empty
     *  selected from a valid province
     */
    private fun isCityValid(city: String): Boolean
    {
        // province will not be empty because it is validated first so no need to check below
        if(city.isEmpty())
        {
            binding.autoCompleteCity.error = "Please select a city"
            return false
        }
       return true
    } // isCity valid




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



