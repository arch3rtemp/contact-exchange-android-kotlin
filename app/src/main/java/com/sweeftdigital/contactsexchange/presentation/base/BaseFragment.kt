package com.sweeftdigital.contactsexchange.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEffect
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEvent
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiState
import kotlinx.coroutines.launch

abstract class BaseFragment<Event: UiEvent, Effect : UiEffect, State : UiState, VB : ViewBinding, VM : BaseViewModel<Event, Effect, State>> : Fragment() {

    private var _binding: VB? = null
    abstract val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> VB
    protected abstract val viewModel: VM
    protected val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindLayout(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            renderState(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    renderEffect(it)
                }
            }
        }

        prepareView(savedInstanceState)
    }

    abstract fun prepareView(savedInstanceState: Bundle?)

    abstract fun renderState(state: State)

    abstract fun renderEffect(effect: Effect)

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}