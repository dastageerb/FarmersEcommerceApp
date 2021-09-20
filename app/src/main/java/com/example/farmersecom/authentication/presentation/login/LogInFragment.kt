package com.example.farmersecom.authentication.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentLogInBinding


class LogInFragment : BaseFragment<FragmentLogInBinding>() , View.OnClickListener
{

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentLogInBinding
    {
       return FragmentLogInBinding.inflate(layoutInflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLoginFragLogin.setOnClickListener(this)
        binding.buttonLoginFragRegister.setOnClickListener(this)

    } // onViewCreated

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
           R.id.buttonLoginFragLogin -> logIn()
           R.id.buttonLoginFragRegister -> findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
        } // when closed
    } // onClick closed


    private fun logIn()
    {

    }


} // LogInFragment