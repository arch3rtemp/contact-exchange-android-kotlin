package dev.arch3rtemp.contactexchange.presentation.edit

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dev.arch3rtemp.contactexchange.databinding.FragmentCardEditBinding
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.core_ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditCardFragment : BaseFragment<EditCardEvent, EditCardEffect, EditCardState, FragmentCardEditBinding, EditCardViewModel>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardEditBinding
        get() = FragmentCardEditBinding::inflate

    override val viewModel by viewModel<EditCardViewModel>()
    private val args by navArgs<EditCardFragmentArgs>()

    override fun prepareView(savedInstanceState: Bundle?) {
        if (viewModel.currentState?.viewState is ViewState.Idle) {
            viewModel.setEvent(EditCardEvent.OnCardLoad(args.id))
        }
        setListeners()
    }

    private fun setListeners() {
        binding.btnUpdate.setOnClickListener {
            viewModel.setEvent(EditCardEvent.OnSaveButtonPressed(getDataFromFields()))
        }
    }

    private fun initCard(contact: Contact) = with(binding) {
        etFullName.setText(contact.name)
        etCompany.setText(contact.job)
        etPosition.setText(contact.position)
        etEmail.setText(contact.email)
        etTel.setText(contact.phoneMobile)
        etTelOffice.setText(contact.phoneOffice)
        clEdit.background.colorFilter = PorterDuffColorFilter(contact.color, PorterDuff.Mode.SRC_IN)
    }

    private fun getDataFromFields(): Contact = with(binding) {
        return Contact(
            name = etFullName.text.toString(),
            job = etCompany.text.toString(),
            position = etPosition.text.toString(),
            email = etEmail.text.toString(),
            phoneMobile = etTel.text.toString(),
            phoneOffice = etTelOffice.text.toString()
        )
    }

    override fun renderState(state: EditCardState) {
        when (state.viewState) {
            ViewState.Idle -> Unit
            ViewState.Loading -> Unit
            ViewState.Error -> Unit
            is ViewState.Success -> initCard(state.viewState.data)
        }
    }

    override fun renderEffect(effect: EditCardEffect) {
        when (effect) {
            is EditCardEffect.Error -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
            }
            EditCardEffect.Finish -> findNavController().navigateUp()
        }
    }
}
