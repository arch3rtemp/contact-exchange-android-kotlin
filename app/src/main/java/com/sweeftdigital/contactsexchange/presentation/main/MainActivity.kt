package com.sweeftdigital.contactsexchange.presentation.main

import android.Manifest
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sweeftdigital.contactsexchange.R
import com.sweeftdigital.contactsexchange.databinding.ActivityMainBinding
import com.sweeftdigital.contactsexchange.presentation.main.home.HomeFragment
import com.sweeftdigital.contactsexchange.presentation.qr.QrActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    val binding: ActivityMainBinding get() = _binding
    private lateinit var navController: NavController

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        openCamera(granted)
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val toastText = resources.getString(R.string.contact_added)
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationWithToolbar()
        setListeners()
    }

    private fun initNavigationWithToolbar() {
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val title = when (destination.id) {
                R.id.homeFragment -> {
                    binding.llBack.visibility = View.INVISIBLE
                    resources.getString(R.string.home_cards)
                }
                else -> {
                    binding.llBack.visibility = View.VISIBLE
                    ""
                }
            }
            supportActionBar?.title = title
        }

//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setListeners() {
        with(binding) {
            llQrScanner.setOnClickListener {
                requestPermission.launch(Manifest.permission.CAMERA)
            }
            llBack.setOnClickListener {
                fragmentContainerView.findNavController().navigateUp()
            }
        }
    }

    private fun openCamera(granted: Boolean) {
        if (granted) {
            val intent = Intent(this, QrActivity::class.java)
            resultLauncher.launch(intent)
        } else {
            val toastText = resources.getString(R.string.permission_denied)
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        }
    }
}