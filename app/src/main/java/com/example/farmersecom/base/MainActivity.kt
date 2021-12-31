package com.example.farmersecom.base

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
import dagger.hilt.android.AndroidEntryPoint

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
            }
        }
        setupActionBarWithNavController(navController,appBarConfiguration)





    } // onCreate


    override fun onSupportNavigateUp(): Boolean
    {
        return navController.navigateUp() || super.onSupportNavigateUp()
    } // onSupportNavigateUp closed
}