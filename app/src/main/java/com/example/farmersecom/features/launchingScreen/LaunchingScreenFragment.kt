package com.example.farmersecom.features.launchingScreen

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentLaunchingScreenBinding
import com.example.farmersecom.utils.constants.Constants.TAG
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class LaunchingScreenFragment : BaseFragment<FragmentLaunchingScreenBinding>() , View.OnClickListener
{

    private val launchingVm:LaunchingScreenViewModel by viewModels()
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentLaunchingScreenBinding
    {
        return  FragmentLaunchingScreenBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
//
//            requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN);


        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        binding.fragmentLaunchingScreenSindhiButton.setOnClickListener(this)
        binding.fragmentLaunchingScreenEnglishButton.setOnClickListener(this)
        binding.fragmentLaunchingScreenButtonContinue.setOnClickListener(this)


        if(!launchingVm.isFirstLaunch())
        {
            findNavController().navigate(R.id.action_launchingScreenFragment_to_homeFragment)
        }else
        {

        }


    } // onViewCreated closed

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.fragmentLaunchingScreenButtonContinue ->
            {
                continueFurther()
            }
            R.id.fragmentLaunchingScreenSindhiButton ->
            {
                setLocale("sd")
            }
            R.id.fragmentLaunchingScreenEnglishButton ->
            {
                setLocale("en")
            }

        } // when closed

    } // onClick closed

    private fun setLocale(language: String)
    {
        Timber.tag(TAG).d(language)
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration =  Configuration()
        configuration.locale = locale
        requireContext().resources.updateConfiguration(configuration,requireContext().resources.displayMetrics)

    }

    private fun continueFurther()
    {

        val quantityButtonId   = binding.fragmentLaunchingScreenLanguageButtonGroup.checkedButtonId

        val language = binding.fragmentLaunchingScreenLanguageButtonGroup
            .findViewById<MaterialButton>(quantityButtonId).text.toString()

        findNavController().navigate(R.id.action_launchingScreenFragment_to_homeFragment)



        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

    } // continueFurther closed

}