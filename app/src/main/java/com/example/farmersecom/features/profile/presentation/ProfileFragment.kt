package com.example.farmersecom.features.profile.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.akhbar.utils.NetworkResource
import com.example.farmersecom.R
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentProfileBinding
import com.example.farmersecom.utils.ContextExtension.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() ,View.OnClickListener
{

    private val viewModel:ProfileViewModel by viewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentProfileBinding
    {
       return FragmentProfileBinding.inflate(inflater,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonProfileFragLogout.setOnClickListener(this)

        requireContext().showToast(""+viewModel.sharedPrefGetToken())

        viewModel.getUser()


        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewModel.userResponse.collect ()
            {
                when(it)
                {
                    is NetworkResource.Error-> requireContext().showToast("${it.msg}")

                    is NetworkResource.Success-> requireContext().showToast("${it.data}")
                }
            }
        }


    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.buttonProfileFragLogout ->
            {
                requireContext().showToast("Working")
                viewModel.clearToken()
                findNavController().navigate(R.id.action_profileFragment_to_logInFragment)
            }
        } // when closed
    } // onClick closed


} // Profile Fragment closed