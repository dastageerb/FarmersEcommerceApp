package com.example.farmersecom.features.profile.presentation.changeLanguage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentChangeLanguageBinding


class ChangeLanguageFragment : BaseFragment<FragmentChangeLanguageBinding>()
{

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentChangeLanguageBinding
    {
        return FragmentChangeLanguageBinding.inflate(inflater,container,false)
    } // createView closed

} // ChangeLanguageFragment