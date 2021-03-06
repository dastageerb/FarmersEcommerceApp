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
import com.example.farmersecom.utils.sealedResponseUtils.NetworkResource
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.hide
import com.example.farmersecom.utils.extensionFunctions.view.ViewExtension.show
import com.example.farmersecom.R
import com.example.farmersecom.features.authentication.data.frameWork.entity.requests.LogInData
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentLogInBinding
import com.example.farmersecom.features.authentication.data.frameWork.entity.responses.User
import com.example.farmersecom.utils.constants.Constants.TAG
import com.example.farmersecom.utils.extensionFunctions.context.ContextExtension.showToast
import com.google.gson.Gson
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>() , View.OnClickListener
{

    private val loginViewModel:LogInViewModel by viewModels()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentLogInBinding
    {
       return FragmentLogInBinding.inflate(layoutInflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLoginFragLogin.setOnClickListener(this)
        binding.buttonLoginFragRegister.setOnClickListener(this)
        binding.buttonLoginFragForgotPassword.setOnClickListener(this)


        subscribeLoginResponseFlow()
        subscribeTokenUpdatedResponse()


    } // onViewCreated

    override fun onStart()
    {
        super.onStart()
    }


    private fun subscribeLoginResponseFlow()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                loginViewModel.loginResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.progressBarLogIn.show()
                            Timber.tag(TAG).d("Loading")
                        }
                        is NetworkResource.Error ->
                        {
                            requireContext().showToast(it.msg!!)
                            Timber.tag(TAG).d(""+it.msg)
                            binding.progressBarLogIn.hide()
                        }
                        is NetworkResource.Success ->
                        {
                         ///   requireContext().showToast(it.data?.message.toString())
                          ///  binding.progressBarLogIn.hide()

                            Timber.tag(TAG).d(it.data.toString())
                            it.data?.let()
                            {
                                data ->
                                data.token?.let { it1 -> loginViewModel.saveAuthToken(it1) }
                                data.user?.let { it1 -> loginViewModel.saveUser(it1)
                                loginViewModel.updateFCMToken()
                                }
                             //   findNavController().navigate(R.id.action_logInFragment_to_homeFragment)

                            }
                        }
                        else -> {}
                    } // when closed
                } // collect closed
            } // repeatOnLifeCycle closed
        } // lifeCycleScope closed
    } // subscribeLoginResponseFlow closed




    private fun subscribeTokenUpdatedResponse()
    {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                loginViewModel.statusMsgResponse.collect()
                {
                    when (it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.progressBarLogIn.show()
                            Timber.tag(TAG).d("Loading")
                        }
                        is NetworkResource.Error ->
                        {
                            binding.progressBarLogIn.hide()
                            Timber.tag(TAG).d("error  " + it.msg)
                            requireContext().showToast("error: ${it.msg}")
                        }
                        is NetworkResource.Success ->
                        {
                            binding.progressBarLogIn.hide()
                            Timber.tag(TAG).d(it.data.toString())
                            findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
                        } ///
                        else ->
                        {
                        }
                    } // when closed
                } // collect closed
            } // repeatOnLifeCycle closed
        } // lifeCycleScope closed
    } // subscribeToTokenUpdateResponse


        override fun onClick(v: View?)
    {
        when(v?.id)
        {
           R.id.buttonLoginFragLogin -> validateAndLogIn()
           R.id.buttonLoginFragRegister -> findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
           R.id.buttonLoginFragForgotPassword-> findNavController().navigate(R.id.action_logInFragment_to_forgotPasswordFragment)
        } // when closed
    } // onClick closed

    private fun validateAndLogIn()
    {

        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        if(validation(email,password))
        {
            Timber.tag(TAG).d("validate email and password $email , $password")
            loginViewModel.loginUser(LogInData(email,password))
        } // if closed
    } // validDateAndLogin closed


    private fun validation(email:String,password: String): Boolean
    {
        return email.validator().
        validEmail().
        nonEmpty()
            .addErrorCallback{ binding.editTextEmail.error = it }
            .check() &&
        password.nonEmpty { binding.editTextPassword.error = it }
    } //




} // LogInFragment