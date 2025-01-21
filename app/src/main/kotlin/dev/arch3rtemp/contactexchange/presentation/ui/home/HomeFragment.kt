package dev.arch3rtemp.contactexchange.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.databinding.FragmentHomeBinding
import dev.arch3rtemp.contactexchange.presentation.model.ContactUi
import dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.delegate.CardDelegate
import dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.delegate.ContactDelegate
import dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.listener.CardClickListener
import dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.listener.ContactClickListener
import dev.arch3rtemp.ui.base.BaseFragment
import dev.arch3rtemp.ui.view.AppSearchView
import dev.arch3rtemp.ui.view.listadapter.DefaultListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeEvent, HomeEffect, HomeState, FragmentHomeBinding, HomeViewModel>(), CardClickListener, ContactClickListener {

    override val viewModel: HomeViewModel by viewModel()
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    private val cardsAdapter: DefaultListAdapter by lazy { DefaultListAdapter(listOf(CardDelegate(this))) }
    private val contactsAdapter: DefaultListAdapter by lazy { DefaultListAdapter(listOf(ContactDelegate(this))) }
    private var searchItem: MenuItem? = null

    override fun prepareView(savedInstanceState: Bundle?) {
        setupToolbar()
        setupRecyclerView()
        setListeners()
    }

    override fun renderState(state: HomeState) = with(binding) {
        renderCardsState(state.cardsState)
        renderContactsState(state.contactsState)
    }

    override fun renderEffect(effect: HomeEffect) {
        when (effect) {
            is HomeEffect.ShowError -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
            }
            is HomeEffect.ShowUndo -> {
                Snackbar.make(requireView(), getString(R.string.msg_contact_deleted), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.msg_undo)) { viewModel.setEvent(HomeEvent.OnContactSave(effect.contact)) }
                    .show()
            }
        }
    }

    private fun renderCardsState(state: CardState) {
        when (state) {
            CardState.Idle -> viewModel.setEvent(HomeEvent.OnCardsLoad)
            CardState.Loading -> { showCardsLoading() }
            CardState.Empty -> { showCardsEmpty() }
            is CardState.Error -> {
                showCardsError()
                binding.tvCardsErrorDesc.text = state.message
            }
            is CardState.Success -> {
                showCardsSuccess()
                cardsAdapter.submitList(state.data)
            }
        }
    }

    private fun renderContactsState(state: ContactState) {
        when (state) {
            ContactState.Idle -> viewModel.setEvent(HomeEvent.OnContactsLoad)
            ContactState.Loading -> { showContactsLoading() }
            ContactState.Empty -> { showContactsEmpty() }
            is ContactState.Error -> {
                showContactsError()
                binding.tvContactsErrorDesc.text = state.message
            }
            is ContactState.Success -> {
                showContactsSuccess()
                contactsAdapter.submitList(state.data)
            }
        }
    }

    private fun setupToolbar() = with(binding) {

        searchItem = toolbarContact.menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as AppSearchView
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.setEvent(HomeEvent.OnSearchTyped(newText!!))
                    return false
                }

            }
        )
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvCards.adapter = cardsAdapter
            rvContacts.adapter = contactsAdapter
        }
        observeRecyclerListener()
    }

    private fun setListeners() {
        with(binding) {
            fabAdd.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCreateCardFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun observeRecyclerListener() {
        with(binding) {
            rvCards.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // If scrolling towards the left (dx < 0), hide the FAB
                    if (dx > 0 && fabAdd.isVisible) {
                        fabAdd.hide()
                    }

                    // If scrolling towards the right (dx > 0), show the FAB
                    if (dx < 0 && !fabAdd.isVisible) {
                        fabAdd.show()
                    }
                }
            })
        }
    }

    override fun onContactClick(id: Int) {
        goToCardFragment(id)
    }

    override fun onCardClick(id: Int) {
        goToCardFragment(id)
    }

    override fun onDeleteClick(contact: ContactUi) {
        viewModel.setEvent(HomeEvent.OnContactDelete(contact))
    }

    private fun goToCardFragment(id: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCardFragment(id))
    }

    private fun showCardsEmpty() = with(binding) {
        ivCardsEmpty.isVisible = true
        tvCardsErrorDesc.isVisible = false
        progressCircularCards.isVisible = false
        rvCards.visibility = View.INVISIBLE
    }

    private fun showCardsLoading() = with(binding) {
        progressCircularCards.isVisible = true
        ivCardsEmpty.isVisible = false
        tvCardsErrorDesc.isVisible = false
        rvCards.visibility = View.INVISIBLE
    }

    private fun showCardsError() = with(binding) {
        tvCardsErrorDesc.isVisible = true
        rvCards.visibility = View.INVISIBLE
        progressCircularCards.isVisible = false
        ivCardsEmpty.isVisible = false
    }

    private fun showCardsSuccess() = with(binding) {
        rvCards.isVisible = true
        progressCircularCards.isVisible = false
        ivCardsEmpty.isVisible = false
        tvCardsErrorDesc.isVisible = false
    }

    private fun showContactsEmpty() = with(binding) {
        ivContactsEmpty.isVisible = true
        progressCircularContacts.isVisible = false
        tvContactsErrorDesc.isVisible = false
        rvContacts.isVisible = false
        searchItem?.isVisible = true
    }

    private fun showContactsLoading() = with(binding) {
        progressCircularContacts.isVisible = true
        ivContactsEmpty.isVisible = false
        tvContactsErrorDesc.isVisible = false
        rvContacts.isVisible = false
        searchItem?.isVisible = true
    }

    private fun showContactsError() = with(binding) {
        tvContactsErrorDesc.isVisible = true
        rvContacts.isVisible = false
        progressCircularContacts.isVisible = false
        ivContactsEmpty.isVisible = false
        searchItem?.isVisible = false
    }

    private fun showContactsSuccess() = with(binding) {
        rvContacts.isVisible = true
        tvContactsErrorDesc.isVisible = false
        progressCircularContacts.isVisible = false
        ivContactsEmpty.isVisible = false
        searchItem?.isVisible = true
    }
}
