package com.example.farmersecom.features.profile.presentation.editPersonalInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentEditPersonalInfoBinding
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.RegisterData
import com.example.farmersecom.features.authentication.presentation.register.utils.Utils.setUpAdapter
import com.example.farmersecom.features.profile.domain.model.UserInfoResponse.UserInfoResponse
import com.example.farmersecom.features.profile.domain.model.editPersonalProfile.EditPersonalInfoEntity
import com.example.farmersecom.features.profile.presentation.ProfileViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.textEqualTo
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.sql.Time
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class EditPersonalInfoFragment :BaseFragment<FragmentEditPersonalInfoBinding>()
{


    private val viewModel: ProfileViewModel by activityViewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentEditPersonalInfoBinding
    {
        return FragmentEditPersonalInfoBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        initViews(viewModel.userInfoResponse)

        binding.buttonRegFragUpdate.setOnClickListener()
        {
            updatePersonalInfo()
        }

        subscribeEditPersonalInfoResponseFlow()

    } // onViewCreated

    private fun initViews(userInfoResponse: UserInfoResponse?)
    {

        Log.d(TAG, "initViews: "+userInfoResponse.toString())


        binding.editTextRegisterFragFirstName.setText(userInfoResponse?.firstName)
        binding.editTextRegisterFragLastName.setText(userInfoResponse?.lastName)
        binding.editTextRegisterFragContact.setText(userInfoResponse?.contactNumber)
        binding.editTextRegisterFragDate.setText(userInfoResponse?.dateOfBirth)
        binding.fragmentEditPersonalInfoCityAutoComplete.setText(userInfoResponse?.city)
        binding.fragmentEditPersonalInfoCityAutoComplete.setUpAdapter(requireContext(),R.array.Sindh)
        binding.editTextRegisterFragAddress.setText(userInfoResponse?.address)
        binding.editTextRegisterFragPostalCode.setText(userInfoResponse?.postalCode.toString())

        if(userInfoResponse?.gender!!.equals("Male",true))
        {
            binding.radioGroupRegisterFrag.check(R.id.fragmentEditPersonalInfoRadioButtonMale)
        }else
        {
            binding.radioGroupRegisterFrag.check(R.id.fragmentEditPersonalInfoRadioButtonFemale)
        }

    } // initViews closed



    private fun updatePersonalInfo()
    {


        val fName  = binding.editTextRegisterFragFirstName.text.toString().trim() // firstName
        val lName = binding.editTextRegisterFragLastName.text.toString().trim() // lastName
        val contact = binding.editTextRegisterFragContact.text.toString().trim()

        /// Get Selected Radio Button Id first
        val radioButtonId   = binding.radioGroupRegisterFrag.checkedRadioButtonId
        // then get Gender based on selected button
        val gender = binding.radioGroupRegisterFrag
            .findViewById<RadioButton>(radioButtonId).text.toString()

        val dateOfBirth = binding.editTextRegisterFragDate.text.toString().trim() // date input
        val city = binding.fragmentEditPersonalInfoCityAutoComplete.text.toString().trim()
        val address = binding.editTextRegisterFragAddress.text.toString().trim()
        val postalCode = binding.editTextRegisterFragPostalCode.text.toString().trim()


        val validPersonalInfo = isPersonalInfoValid(fName,lName,contact)
        val validDob = isValidDate(dateOfBirth)
        val validCity =  isCityValid(city)
        val validPostalInfo = isPostalInfoValid(address,postalCode)

        if(validPersonalInfo && validDob &&   validCity && validPostalInfo)
        {
            val editPersonalInfoEntity = EditPersonalInfoEntity(address,city,contact
            ,dateOfBirth,fName,gender,lName,postalCode.toInt())
            viewModel.editPersonalInfoUseCase(editPersonalInfoEntity)
        } // if closed

    } // register closed


    /** valid the personal info
     * this method uses a library Easy validation by Sir @Wajah_Karim
     * */

    private fun isPersonalInfoValid(fName: String, lName: String, contact: String):Boolean
    {
        return fName.nonEmpty { binding.editTextRegisterFragFirstName.error = it }
                && lName.nonEmpty { binding.editTextRegisterFragLastName.error = it }
                && contact.validator()
            .nonEmpty()
            .minLength(11)
            .maxLength(11)
            .addErrorCallback { binding.editTextRegisterFragContact.error = it }
            .check()

    } // isPersonalInfoValid




    private fun isValidDate(date: String): Boolean
    {
        if(!date.nonEmpty())
        {
            binding.editTextRegisterFragDate.error = "date is empty "
            return false
        }
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
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
            requireContext().showToast(getString(R.string.plz_select_city))
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


    private fun subscribeEditPersonalInfoResponseFlow()
    {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.editPersonalInfoResponse.collect()
                {
                    when (it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.progressBarEditPersonalInfoFrag.show()
                            Timber.tag(TAG).d("Loading")
                        }
                        is NetworkResource.Success ->
                        {
                            binding.progressBarEditPersonalInfoFrag.hide()
                            requireContext().showToast(it.data?.message.toString())
                            // save User here
                            it.data?.user?.let { it1 -> viewModel.saveUser(it1) }
                            findNavController().navigate(R.id.action_editPersonalInfoFragment_to_fullUserProfileFragment)
                        }
                        is NetworkResource.Error ->
                        {
                            binding.progressBarEditPersonalInfoFrag.hide()
                            requireContext().showToast(it.msg.toString())
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscriber closed


} // EditPersonalInfoFragment