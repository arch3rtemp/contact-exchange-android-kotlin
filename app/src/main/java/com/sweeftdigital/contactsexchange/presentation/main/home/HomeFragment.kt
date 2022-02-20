package com.sweeftdigital.contactsexchange.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sweeftdigital.contactsexchange.databinding.FragmentHomeBinding
import com.sweeftdigital.contactsexchange.presentation.main.home.adapter.ContactsListAdapter

class HomeFragment : Fragment(), ContactsListAdapter.ClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        with(binding) {
            fabAdd.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCreateCardFragment()
                findNavController().navigate(action)
            }
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