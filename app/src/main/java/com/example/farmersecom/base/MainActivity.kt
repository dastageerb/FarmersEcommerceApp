package com.example.farmersecom.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.farmersecom.R
import com.example.farmersecom.databinding.ActivityMainBinding
import com.example.farmersecom.utils.constants.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    private lateinit var navController: NavController;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        val appBarConfiguration = AppBarConfiguration(
            setOf
                (
                R.id.homeFragment
                ,R.id.searchFragment
                ,R.id.communityFragment
                ,R.id.cartFragment
                ,R.id.profileFragment
            ))

        binding.bottomNavigationViewMainActivity.setupWithNavController(navController)

        binding.bottomNavigationViewMainActivity.setOnItemReselectedListener ()
        {
            when(it.itemId)
            {
                R.id.profileFragment ->
                {
                    if(navController.currentDestination?.id != R.id.profileFragment)
                    {
                        navController.navigate(R.id.profileFragment)
                    }
                }
                R.id.communityFragment ->
                {
                    if(navController.currentDestination?.id != R.id.communityFragment)
                    {
                    navController.navigate(R.id.communityFragment)
                    }
                }
                R.id.searchFragment ->
                {
                    if(navController.currentDestination?.id != R.id.searchFragment)
                    {
                        navController.navigate(R.id.searchFragment)
                    }
                }
                R.id.homeFragment ->
                {
                    if(navController.currentDestination?.id != R.id.homeFragment)
                    {
                        navController.navigate(R.id.homeFragment)
                    }
                }
                R.id.cartFragment->
                {
                    if(navController.currentDestination?.id != R.id.cartFragment)
                    {
                        navController.navigate(R.id.cartFragment)
                    }
                }


            } /// when closed
        }
        setupActionBarWithNavController(navController,appBarConfiguration)





    } // onCreate


    override fun onNewIntent(intent: Intent?)
    {
        super.onNewIntent(intent)

        Timber.tag(TAG).d("Called")
        Timber.tag(TAG).d(""+intent?.extras?.get("for"))

        when(intent?.extras?.get("for"))
        {
            1 ->
            {

                Timber.tag(TAG).d(""+intent?.extras?.get("for"))
                navController.navigate(R.id.activeOrdersFragment)
            }
            2->
            {

                Timber.tag(TAG).d(""+intent?.extras?.get("for"))
                navController.navigate(R.id.currentOrdersFragment)
            }
            3 ->
            {
                Timber.tag(TAG).d(""+intent?.extras?.get("for"))
                navController.navigate(R.id.homeFragment)
            }
        } // when closed

    } // intent closed

    override fun onSupportNavigateUp(): Boolean
    {
        return navController.navigateUp() || super.onSupportNavigateUp()
    } // onSupportNavigateUp closed
}