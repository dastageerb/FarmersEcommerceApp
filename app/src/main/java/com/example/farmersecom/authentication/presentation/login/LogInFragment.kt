package com.example.farmersecom.authentication.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.akhbar.utils.NetworkResource
import com.example.akhbar.utils.ViewExtension.hide
import com.example.akhbar.utils.ViewExtension.show
import com.example.farmersecom.R
import com.example.farmersecom.authentication.data.entity.requests.LogInEntity
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentLogInBinding
import com.example.farmersecom.utils.Constants.TAG
import com.example.farmersecom.utils.ContextExtension.showToast
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
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


        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
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
                        binding.progressBarLogIn.hide()
                        Timber.tag(TAG).d(it.msg)
                    }
                    is NetworkResource.Success ->
                    {
                        requireContext().showToast(it.data.toString())
                        Timber.tag(TAG).d(it.data.toString())
                    }
                    else -> {}
                }
            }
        }

    } // onViewCreated

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
           R.id.buttonLoginFragLogin -> validateAndLogIn()
           R.id.buttonLoginFragRegister -> findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
        } // when closed
    } // onClick closed

    private fun validateAndLogIn()
    {

        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.toString().trim();

        if(validation(email,password))
        {
            loginViewModel.loginUser(LogInEntity(email,password))
        } // if closed
    } // validDateAndLogin closed


    private fun validation(email:String,password: String): Boolean
    {
        return email.validator().
        validEmail().
        nonEmpty()
            .addErrorCallback{ binding.editTextEmail.error = it }
            .check() &&
        password.nonEmpty() { binding.editTextPassword.error = it }

    } //




} // LogInFragment