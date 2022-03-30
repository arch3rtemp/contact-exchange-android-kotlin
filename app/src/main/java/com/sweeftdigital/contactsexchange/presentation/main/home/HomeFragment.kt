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
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sweeftdigital.contactsexchange.databinding.FragmentHomeBinding
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.presentation.base.BaseFragment
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.ContactsListAdapter
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.CardItemDrawer
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.ContactItemDrawer
import com.sweeftdigital.contactsexchange.util.TextWatcherTrimmed
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeEvent, HomeEffect, HomeState, FragmentHomeBinding, HomeViewModel>(), ContactsListAdapter.ClickListener {

    override val viewModel: HomeViewModel by viewModel()
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    private val cardsAdapter: ContactsListAdapter by lazy { ContactsListAdapter(this@HomeFragment) }
    private val contactsAdapter: ContactsListAdapter by lazy { ContactsListAdapter(this@HomeFragment) }
    private var filtering = false
    private var backPressedTimestamp: Long = 0L

    private lateinit var filterTextWatcher: TextWatcher
    private lateinit var backPressedDispatcher: OnBackPressedCallback

    override fun prepareView(savedInstanceState: Bundle?) {
        initRecyclerView()
        initObservers()
        setListeners()
    }

    override fun renderState(state: HomeState) = with(binding) {
        renderCardsState(state.cardsState)
        renderContactsState(state.contactsState)
    }

    private fun initRecyclerView() {
        with(binding) {
            rvCards.adapter = cardsAdapter
            rvContacts.adapter = contactsAdapter
        }
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
                Snackbar.make(requireView(), effect.message, Snackbar.LENGTH_SHORT).show()
            }
            is HomeEffect.Deleted -> {
                effect.contact
                Snackbar
                    .make(requireView(), "Contact Deleted", Snackbar.LENGTH_SHORT)
                    .setAction("UNDO") {
                        viewModel.saveContact(effect.contact)
                    }
                    .show()
            }
        }
    }

    private fun initObservers() {
        observeRecyclerListener()
        initializeTextWatcher()
        initializeBackPressedCallback()
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
        backPressedDispatcher =
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                if (backPressedTimestamp + TIME_INTERVAL > System.currentTimeMillis()) {
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
                contactsAdapter.filterContacts(s)
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
            val moveLeftAnimation = ObjectAnimator.ofFloat(ivSearch, View.X, 0.0f).apply {
                duration = 400
                interpolator = DecelerateInterpolator()
            }
            val fadeInAnimation = ObjectAnimator.ofFloat(tvContactHeader, View.ALPHA, 1f, 0f)

            AnimatorSet().apply {
                playTogether(moveLeftAnimation, fadeInAnimation)
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {
                        tvContactHeader.visibility = View.INVISIBLE
                        etSearch.visibility = View.VISIBLE
                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationRepeat(animation: Animator?) {}

                })
            }.start()
        }
    }

    private fun animateSearchBackward() {
        with(binding) {
            filtering = false
            val moveRightAnimation =
                ObjectAnimator.ofFloat(ivSearch, View.X, etSearch.width.toFloat()).apply {
                    duration = 400
                    interpolator = DecelerateInterpolator()
                }
            val fadeOutAnimation = ObjectAnimator.ofFloat(tvContactHeader, View.ALPHA, 0f, 1f)

            AnimatorSet().apply {
                playTogether(moveRightAnimation, fadeOutAnimation)
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                        tvContactHeader.visibility = View.VISIBLE
                        etSearch.visibility = View.INVISIBLE
                    }

                    override fun onAnimationEnd(animation: Animator?) {}

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationRepeat(animation: Animator?) {}

                })
            }.start()
        }
    }

    override fun onContactClicked(id: Int) {
        Toast.makeText(requireContext(), "Contact Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onCardClicked(id: Int) {
        Toast.makeText(requireContext(), "Card Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClicked(contact: Contact) {
        viewModel.deleteContact(contact)
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