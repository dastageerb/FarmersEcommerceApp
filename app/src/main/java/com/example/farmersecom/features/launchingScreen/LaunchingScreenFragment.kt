package com.example.farmersecom.features.launchingScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentLaunchingScreenBinding
import com.example.farmersecom.utils.constants.Constants.TAG
import com.ninenox.kotlinlocalemanager.LocaleManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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


        if(!launchingVm.isFirstLaunch())
        {
            Timber.tag(TAG).d("check launch"+launchingVm.isFirstLaunch())
            findNavController().navigate(R.id.action_launchingScreenFragment_to_homeFragment)
           // (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        }
        else
        {
            Timber.tag(TAG).d("check launch"+launchingVm.isFirstLaunch())

        }

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        binding.fragmentLaunchingScreenSindhiButton.setOnClickListener(this)
        binding.fragmentLaunchingScreenEnglishButton.setOnClickListener(this)
        binding.fragmentLaunchingScreenButtonContinue.setOnClickListener(this)




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
        val localeManager:LocaleManager = LocaleManager(requireContext())
        localeManager.setNewLocale(requireContext(),language)
        LocaleManager(requireContext()).language.toString()
        activity?.recreate()

//        Timber.tag(TAG).d(language)
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//        val configuration =  Configuration()
//        configuration.locale = locale
//        requireContext().resources.updateConfiguration(configuration,requireContext().resources.displayMetrics)

    }

    private fun continueFurther()
    {

        launchingVm.changeFirstLaunch(false)
        findNavController().navigate(R.id.action_launchingScreenFragment_to_homeFragment)
      //  (activity as AppCompatActivity?)!!.supportActionBar!!.show()

    } // continueFurther closed

}