package dev.arch3rtemp.contactexchange.presentation.ui

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
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.databinding.ActivityMainBinding
import dev.arch3rtemp.contactexchange.domain.usecase.ScanQrUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val scanner: ScanQrUseCase by inject { parametersOf(this) }
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
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
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
    }

    private fun setListeners() {
        with(binding) {

            llQrScanner.setOnClickListener {
                startScanner()
            }

            llBack.setOnClickListener {
                navController.navigateUp()

                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm.isActive) {
                    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
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

    private fun startScanner() {
        lifecycleScope.launch {
            try {
                val contact = scanner()
                viewModel.setEvent(MainEvent.OnQrScanComplete(contact))
            } catch (cancellation: CancellationException) {
                viewModel.setEvent(MainEvent.OnQrScanCanceled(cancellation.localizedMessage))
            } catch (exception: Exception) {
                viewModel.setEvent(MainEvent.OnQrScanFail(exception.localizedMessage))
            }
        }
    }
}
