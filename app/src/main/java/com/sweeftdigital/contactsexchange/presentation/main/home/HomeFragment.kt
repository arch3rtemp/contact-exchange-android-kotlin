package com.sweeftdigital.contactsexchange.presentation.main.home

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sweeftdigital.contactsexchange.databinding.FragmentHomeBinding
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.presentation.base.BaseFragment
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.ContactsListAdapter
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.CardItemDrawer
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.ContactItemDrawer
import com.sweeftdigital.contactsexchange.util.custom_segregation.AnimatorListenerOnAnimationEnd
import com.sweeftdigital.contactsexchange.util.custom_segregation.AnimatorListenerOnAnimationStart
import com.sweeftdigital.contactsexchange.util.custom_segregation.TextWatcherTrimmed
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment :
    BaseFragment<HomeEvent, HomeEffect, HomeState, FragmentHomeBinding, HomeViewModel>(),
    ContactsListAdapter.ClickListener {

    override val viewModel: HomeViewModel by viewModel()
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    private val cardsAdapter: ContactsListAdapter by lazy { ContactsListAdapter(this@HomeFragment) }
    private val contactsAdapter: ContactsListAdapter by lazy { ContactsListAdapter(this@HomeFragment) }
    private var filtering = false
    private var backPressedTimestamp: Long = 0L

    private lateinit var filterTextWatcher: TextWatcher

    override fun prepareView(savedInstanceState: Bundle?) {
        initRecyclerView()
        initObservers()
        setListeners()
    }

    private fun initRecyclerView() {
        with(binding) {
            rvCards.adapter = cardsAdapter
            rvContacts.adapter = contactsAdapter
        }
    }

    private fun initObservers() {
        observeRecyclerListener()
        initializeTextWatcher()
        initializeBackPressedCallback()
    }

    private fun setListeners() {
        with(binding) {
            fabAdd.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCreateCardFragment()
                findNavController().navigate(action)
            }
            ivSearch.setOnClickListener {
                animateSearchMenu()
            }
            etSearch.addTextChangedListener(filterTextWatcher)
        }
    }

    private fun observeRecyclerListener() {
        with(binding) {
            rvCards.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dx > 0) {
                        fabAdd.visibility = View.INVISIBLE
                    } else {
                        fabAdd.visibility = View.VISIBLE
                    }
                }
            })
        }
    }

    private fun initializeBackPressedCallback() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (filtering) {
                animateSearchBackward()
                filtering = false
                return@addCallback
            }
            if (backPressedTimestamp + TIME_INTERVAL > System.currentTimeMillis()) {
                isEnabled = false
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Press back again to exit",
                    Toast.LENGTH_SHORT
                ).show()
            }
            backPressedTimestamp = System.currentTimeMillis()
        }
    }

    private fun initializeTextWatcher() {
        filterTextWatcher = object : TextWatcherTrimmed {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setEvent(HomeEvent.OnSearchTyped(s.toString()))
            }
        }
    }

    private fun animateSearchMenu() {
        if (filtering) {
            animateSearchBackward()
        } else {
            animateSearchForward()
        }
    }

    private fun animateSearchForward() {
        with(binding) {
            filtering = true
            val moveLeftAnimation =
                ObjectAnimator.ofFloat(ivSearch, View.X, ivSearch.translationX).apply {
                    duration = 400
                    interpolator = DecelerateInterpolator()
                }
            val fadeInAnimation = ObjectAnimator.ofFloat(tvContactHeader, View.ALPHA, 1f, 0f)

            AnimatorSet().apply {
                playTogether(moveLeftAnimation, fadeInAnimation)
                addListener(object : AnimatorListenerOnAnimationEnd {
                    override fun onAnimationEnd(animation: Animator) {
                        tvContactHeader.visibility = View.INVISIBLE
                        etSearch.visibility = View.VISIBLE
                    }
                })
            }.start()
        }
    }

    private fun animateSearchBackward() {
        with(binding) {
            filtering = false
            val moveRightAnimation =
                ObjectAnimator.ofFloat(ivSearch, View.X, ivSearch.x - ivSearch.translationX).apply {
                    duration = 400
                    interpolator = DecelerateInterpolator()
                }
            val fadeOutAnimation = ObjectAnimator.ofFloat(tvContactHeader, View.ALPHA, 0f, 1f)

            AnimatorSet().apply {
                playTogether(moveRightAnimation, fadeOutAnimation)
                addListener(object : AnimatorListenerOnAnimationStart {
                    override fun onAnimationStart(animation: Animator) {
                        tvContactHeader.visibility = View.VISIBLE
                        etSearch.visibility = View.INVISIBLE
                    }
                })
            }.start()
        }
    }

    override fun renderState(state: HomeState) = with(binding) {
        renderCardsState(state.cardsState)
        renderContactsState(state.contactsState)
    }

    private fun renderCardsState(state: CardsState) {
        when (state.viewState) {
            ViewState.Success -> {
                showCardsSuccess()
                val cards = state.myCards.map { card ->
                    CardItemDrawer(card)
                }
                cardsAdapter.modifyList(cards)
            }
            ViewState.Loading -> {
                showCardsLoading()
            }
            ViewState.Error -> {
                showCardsError()
            }
            ViewState.Empty -> {
                showCardsEmpty()
            }
        }
    }

    private fun renderContactsState(state: ContactsState) {
        when (state.viewState) {
            ViewState.Success -> {
                showContactsSuccess()
                val contacts = state.contacts.map { contact ->
                    ContactItemDrawer(contact)
                }
                contactsAdapter.modifyList(contacts)
            }
            ViewState.Loading -> {
                showContactsLoading()
            }
            ViewState.Error -> {
                showContactsError()
            }
            ViewState.Empty -> {
                showContactsEmpty()
            }
        }
    }

    override fun renderEffect(effect: HomeEffect) {
        when (effect) {
            is HomeEffect.Error -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
            }
            is HomeEffect.Deleted -> {
                effect.contact
                Snackbar
                    .make(requireView(), "Contact Deleted", Snackbar.LENGTH_SHORT)
                    .setAction("UNDO") {
                        viewModel.setEvent(HomeEvent.OnContactSaved(effect.contact))
                    }
                    .show()
            }
            is HomeEffect.Searched -> {
                contactsAdapter.filterContacts(effect.searched)
            }
        }
    }

    override fun onContactClicked(id: Int) {
        goToCardFragment(id)
    }

    override fun onCardClicked(id: Int) {
        goToCardFragment(id)
    }

    override fun onDeleteClicked(contact: Contact) {
        viewModel.setEvent(HomeEvent.OnContactDeleted(contact))
    }

    private fun goToCardFragment(id: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCardFragment(id))
    }

    private fun showCardsEmpty() = with(binding) {
        ivCardsEmpty.visibility = View.VISIBLE
        progressCircularCards.visibility = View.INVISIBLE
        ivCardsError.visibility = View.INVISIBLE
        rvCards.visibility = View.INVISIBLE
    }

    private fun showContactsEmpty() = with(binding) {
        ivContactsEmpty.visibility = View.VISIBLE
        progressCircularContacts.visibility = View.INVISIBLE
        ivContactsError.visibility = View.INVISIBLE
        rvContacts.visibility = View.INVISIBLE
    }

    private fun showCardsLoading() = with(binding) {
        progressCircularCards.visibility = View.VISIBLE
        ivCardsEmpty.visibility = View.INVISIBLE
        ivCardsError.visibility = View.INVISIBLE
        rvCards.visibility = View.INVISIBLE
    }

    private fun showContactsLoading() = with(binding) {
        progressCircularContacts.visibility = View.VISIBLE
        ivContactsEmpty.visibility = View.INVISIBLE
        ivContactsError.visibility = View.INVISIBLE
        rvContacts.visibility = View.INVISIBLE
    }

    private fun showCardsSuccess() = with(binding) {
        rvCards.visibility = View.VISIBLE
        progressCircularCards.visibility = View.INVISIBLE
        ivCardsEmpty.visibility = View.INVISIBLE
        ivCardsError.visibility = View.INVISIBLE
    }

    private fun showContactsSuccess() = with(binding) {
        rvContacts.visibility = View.VISIBLE
        progressCircularContacts.visibility = View.INVISIBLE
        ivContactsEmpty.visibility = View.INVISIBLE
        ivContactsError.visibility = View.INVISIBLE
    }

    private fun showCardsError() = with(binding) {
        ivCardsError.visibility = View.VISIBLE
        rvCards.visibility = View.INVISIBLE
        progressCircularCards.visibility = View.INVISIBLE
        ivCardsEmpty.visibility = View.INVISIBLE
    }

    private fun showContactsError() = with(binding) {
        ivCardsError.visibility = View.VISIBLE
        rvCards.visibility = View.INVISIBLE
        progressCircularCards.visibility = View.INVISIBLE
        ivCardsEmpty.visibility = View.INVISIBLE
    }

    companion object {
        private const val TIME_INTERVAL = 2000
    }
}