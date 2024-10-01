package com.awashwinter.testappvideos.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.awashwinter.testappvideos.R
import com.awashwinter.testappvideos.databinding.ActivityMainBinding
import com.awashwinter.testappvideos.viewmodels.ShareViewModel
import com.awashwinter.testappvideos.viewmodels.VideosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val videosViewModel: VideosViewModel by viewModels<VideosViewModel>()
    private val shareDataViewModel: ShareViewModel by viewModels<ShareViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupToolbar()
        setupOnBackPressed()
        setupShareViewModel()
        setupNavigationListener()
        videosViewModel.getVideos()
    }

    private fun setupNavigationListener() {

        navController.addOnDestinationChangedListener { controller, destination, args ->

            when(destination.id) {
                R.id.videoPlayerFragment -> {
                    Log.d("MainActivity", "Current destination: R.id.videoPlayerFragment ${shareDataViewModel.currentMediaData?.name}")
                    binding.toolbar.title = shareDataViewModel.currentMediaData?.name
                }

                R.id.listVideosFragment -> {
                    Log.d("MainActivity", "Current destination: R.id.listVideosFragment")
                    binding.toolbar.title = getString(R.string.app_name)
                }
            }
        }
    }

    private fun setupShareViewModel() {
        shareDataViewModel.liveDataSelectedVideoPosition.observe(this) {
            binding.toolbar.title = shareDataViewModel.currentMediaData?.name
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        val navConfig = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, navConfig)
        binding.toolbar.setupWithNavController(navController, navConfig)
        binding.toolbar.title = shareDataViewModel.currentMediaData?.name
    }

    private fun setupOnBackPressed() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d("[MainActivity]", "Activity back pressed.")
                    // Do custom work here
                    if(!findNavController(R.id.nav_host_fragment).popBackStack()) {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                        isEnabled = true
                    }
                }
            }
        )
    }
}