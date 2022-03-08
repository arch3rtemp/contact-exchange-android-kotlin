package com.sweeftdigital.contactsexchange.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sweeftdigital.contactsexchange.databinding.FragmentHomeBinding
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.ContactsListAdapter
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.CardItemDrawer
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.ContactItemDrawer
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.ItemDrawer
import com.sweeftdigital.contactsexchange.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), ContactsListAdapter.ClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var cardsAdapter: ContactsListAdapter
    private lateinit var contactsAdapter: ContactsListAdapter

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
        setListeners()
        initObservers()
    }

    private fun initRecyclerView() {
        with(binding) {
            cardsAdapter = ContactsListAdapter(this@HomeFragment)
            contactsAdapter = ContactsListAdapter(this@HomeFragment)
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
        }
    }

    private fun initObservers() {
        homeViewModel.state.observe(viewLifecycleOwner) { state ->
            val contacts = state.contacts
                .map { contact ->
                    ContactItemDrawer(contact)
                }
            contactsAdapter.submitList(contacts)

            val cards = state.myCards
                .map { card ->
                    CardItemDrawer(card)
                }
            cardsAdapter.submitList(cards)
        }
    }

    override fun onContactClicked(id: Int) {
        Toast.makeText(requireContext(), "Contact Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onCardClicked(id: Int) {
        Toast.makeText(requireContext(), "Card Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClicked(id: Int) {
        Toast.makeText(requireContext(), "Delete Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}