package com.sweeftdigital.contactsexchange.presentation.main.home

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.ContactsListAdapter
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.CardItemDrawer
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.ContactItemDrawer
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), ContactsListAdapter.ClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()
    private val cardsAdapter: ContactsListAdapter by lazy { ContactsListAdapter(this@HomeFragment) }
    private val contactsAdapter: ContactsListAdapter by lazy {  ContactsListAdapter(this@HomeFragment) }
    private var filtering = false
    private var backPressedTimestamp: Long = 0L

    private lateinit var filterTextWatcher: TextWatcher
    private lateinit var backPressedDispatcher: OnBackPressedCallback


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun initObservers() {
        homeViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeViewState.Success -> {
                    val cards = state.myCards.map { contact ->
                        CardItemDrawer(contact)
                    }
                    cardsAdapter.modifyList(cards)

                    val contacts = state.contacts.map { contact ->
                        ContactItemDrawer(contact)
                    }
                    contactsAdapter.modifyList(contacts)
                }
                is HomeViewState.Loading -> {

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewModel.effect.collect { state ->
                when (state) {
                    is HomeViewState.Effect.Error -> {
                        Snackbar.make(requireView(), state.message, Snackbar.LENGTH_SHORT).show()
                    }
                    is HomeViewState.Effect.Deleted -> {
                        state.contact
                        Snackbar
                            .make(requireView(), "Contact Deleted", Snackbar.LENGTH_SHORT)
                            .setAction("UNDO") {
                                homeViewModel.saveContact(state.contact)
                            }
                            .show()
                    }
                }
            }
        }

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
        backPressedDispatcher = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (backPressedTimestamp + TIME_INTERVAL > System.currentTimeMillis()) {
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(
                    requireContext() ,
                    "Press back again to exit",
                    Toast.LENGTH_SHORT
                ).show()
            }
            backPressedTimestamp = System.currentTimeMillis()
        }
    }

    private fun initializeTextWatcher() {
        filterTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("InterestingError", s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

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
            val moveRightAnimation = ObjectAnimator.ofFloat(ivSearch, View.X, etSearch.width.toFloat()).apply {
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
        homeViewModel.deleteContact(contact)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TIME_INTERVAL = 2000
    }
}