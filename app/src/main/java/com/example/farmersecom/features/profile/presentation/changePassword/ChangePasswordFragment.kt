package com.example.farmersecom.features.profile.presentation.changePassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentChangePasswordBinding
import com.example.farmersecom.features.profile.presentation.ProfileViewModel
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.textEqualTo
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>()
{


    private val viewModel: ProfileViewModel by activityViewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentChangePasswordBinding
    {
        return FragmentChangePasswordBinding.inflate(inflater,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)



        binding.fragmentChangePasswordConfirmButton.setOnClickListener()
        {
            confirmChangingPassword()
        }
        subscribeToChangePasswordResponseFlow()

    } // onViewCreated closed

    private fun confirmChangingPassword()
    {
        val oldPass = binding.fragmentChangePasswordOldPasswordEditText.text.toString().trim() // password
        val newPass = binding.fragmentChangePasswordNewPasswordEditText.text.toString().trim() // password
        val rePass = binding.fragmentChangePasswordReEnteredNewPasswordEditText.text.toString().trim() // Re Entered Password

        if(validation(oldPass,newPass,rePass))
        {
             viewModel.changePassword(oldPass,rePass)
        } // if closed

    } // confirmChangingPassword


    private fun validation(oldPass: String, newPass: String,rePass:String):Boolean
    {
        return oldPass.nonEmpty { binding.fragmentChangePasswordOldPasswordEditText.error = it }
                && newPass.validator()
            .nonEmpty()
            .minLength(8)
            .maxLength(16)
            .addErrorCallback { binding.fragmentChangePasswordNewPasswordEditText.error = it }
            .check()
                && rePass.textEqualTo(newPass){ binding.fragmentChangePasswordReEnteredNewPasswordEditText.error = " Password Does not match "}
    } // isPersonalInfoValid


    private fun subscribeToChangePasswordResponseFlow()
    {
        //
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.statusMsgResponse.collect()
                {
                    when (it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.FragmentChangePasswordProgressBar.show()
                        }
                        is NetworkResource.Success ->
                        {
                            binding.FragmentChangePasswordProgressBar.hide()
                            viewModel.clearToken()
                            viewModel.clearFiltersOnLogOut()
                            viewModel.clearCartOnLogout()
                            findNavController().navigate(R.id.action_changePasswordFragment_to_logInFragment)
                            requireContext().showToast(it.data?.message.toString())
                        }
                        is NetworkResource.Error ->
                        {
                            binding.FragmentChangePasswordProgressBar.hide()
                            requireContext().showToast(it.msg.toString())
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    } // subscriber closed



} //