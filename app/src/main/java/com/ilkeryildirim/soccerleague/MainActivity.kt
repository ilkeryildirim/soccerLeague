package com.ilkeryildirim.soccerleague

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ilkeryildirim.soccerleague.databinding.ActivityMainBinding
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Hawk.init(binding.root.context)
        navController = findNavController(R.id.hostFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.previousBackStackEntry?.let{ previousBackStackEntry->
           navController.navigate(previousBackStackEntry.destination.id)
        }.run {
            navController.navigateUp()
        }
        return true
    }
}