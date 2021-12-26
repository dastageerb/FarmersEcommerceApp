package com.example.farmersecom.features.authentication.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentForgotPasswordBinding
import com.example.farmersecom.utils.constants.Constants
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>()
{

    private val loginViewModel:LogInViewModel by viewModels()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentForgotPasswordBinding
    {
        return FragmentForgotPasswordBinding.inflate(inflater,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        binding.fragmentForgotPasswordSubmitButton.setOnClickListener()
        {
            forgotPassword()
        }


        subscribeToForgotPasswordResponse()

    } // onViewCreated closed

    private fun subscribeToForgotPasswordResponse()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                loginViewModel.forgotPasswordResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.fragmentForgotPasswordProgressBar.show()
                        }
                        is NetworkResource.Success ->
                        {
                            it.data?.message?.let { it1 -> requireContext().showToast(it1) }
                            binding.fragmentForgotPasswordProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.data?.message}")
                            loginViewModel.clearToken()
                            findNavController().navigate(R.id.action_forgotPasswordFragment_to_logInFragment)

                        }
                        is NetworkResource.Error ->
                        {
                            binding.fragmentForgotPasswordProgressBar.hide()
                            Timber.tag(Constants.TAG).d("${it.msg}")
                        }
                    }// when closed
                } // getProfile closed
            } // repeatOnLife cycle closed
        } /// lifecycleScope closed
    }


    private fun forgotPassword()
    {

        val email = binding.fragmentForgotPasswordEmailEditText.text.toString().trim()
        if(validation(email))
        {
            loginViewModel.forgotPassword(email)
        } // if closed
    } // validDateAndLogin closed


    private fun validation(email:String): Boolean
    {
        return email.validator().
        validEmail().
        nonEmpty()
            .addErrorCallback{ binding.fragmentForgotPasswordEmailEditText.error = it }
            .check()
    } //



} // ForgotPassword