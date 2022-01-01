package com.example.farmersecom.features.communitySection.presentation.editPost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentEditPostBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditPostFragment : BaseFragment<FragmentEditPostBinding>()
{

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentEditPostBinding
    {
        return FragmentEditPostBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

    } // onViewCreated


} // EditPostFragment