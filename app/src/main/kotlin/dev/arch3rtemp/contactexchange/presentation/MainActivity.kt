package dev.arch3rtemp.contactexchange.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.data.mapper.jsonToContact
import dev.arch3rtemp.contactexchange.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationWithToolbar()
        setListeners()
        setObservables()
    }

    private fun initNavigationWithToolbar() {
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
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
                initScanner()
            }

            llBack.setOnClickListener {
                navController.navigateUp()
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    if (imm.isActive) {
                        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
                    }
                }
            }
        }
    }

    private fun setObservables() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    renderEffect(it)
                }
            }
        }
    }

    private fun renderEffect(effect: MainEffect) {
        when (effect) {
            is MainEffect.ShowMessage -> Toast.makeText(this, effect.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initScanner() {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .enableAutoZoom()
            .build()

        val scanner = GmsBarcodeScanning.getClient(this, options)
        scanner
            .startScan()
            .addOnSuccessListener { barcode ->
                val value = barcode.rawValue
                if (value != null) {
                    val jsonObj = JSONObject(value)
                    val contact = jsonToContact(jsonObj)
                    viewModel.setEvent(MainEvent.OnQrScanComplete(contact))
                } else {
                    viewModel.setEvent(MainEvent.OnQrScanFail(null))
                }
            }
            .addOnCanceledListener {
                viewModel.setEvent(MainEvent.OnQrScanCanceled)
            }
            .addOnFailureListener { e ->
                viewModel.setEvent(MainEvent.OnQrScanFail(e.message))
            }
    }
}
