package com.example.farmersecom.features.profile.presentation.changeLanguage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentChangeLanguageBinding
import com.ninenox.kotlinlocalemanager.LocaleManager


class ChangeLanguageFragment : BaseFragment<FragmentChangeLanguageBinding>() , View.OnClickListener
{

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentChangeLanguageBinding
    {
        return FragmentChangeLanguageBinding.inflate(inflater,container,false)
    } // createView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        binding.fragmentChangeLanguageSindhiButton.setOnClickListener(this)
        binding.fragmentChangeLanguageEnglishButton.setOnClickListener(this)




    } // onViewCreated closed

    override fun onClick(v: View?)
    {
        when(v?.id)
        {

            R.id.fragmentChangeLanguageSindhiButton ->
            {
                setLocale("sd")
            }
            R.id.fragmentChangeLanguageEnglishButton ->
            {
                setLocale("en")
            }

        } // when closed

    } // onClick closed

    private fun setLocale(language: String)
    {
        val localeManager: LocaleManager = LocaleManager(requireContext())
        localeManager.setNewLocale(requireContext(),language)
        LocaleManager(requireContext()).language.toString()
        activity?.recreate()

    }

    private fun continueFurther()
    {

      //  findNavController().navigate(R.id.action_launchingScreenFragment_to_homeFragment)
        //  (activity as AppCompatActivity?)!!.supportActionBar!!.show()

    } // continueFurther closed





} // ChangeLanguageFragment