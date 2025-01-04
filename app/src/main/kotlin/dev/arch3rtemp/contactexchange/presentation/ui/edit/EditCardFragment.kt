package dev.arch3rtemp.contactexchange.presentation.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dev.arch3rtemp.contactexchange.databinding.FragmentCardEditBinding
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.presentation.model.ContactUi
import dev.arch3rtemp.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditCardFragment : BaseFragment<EditCardEvent, EditCardEffect, EditCardState, FragmentCardEditBinding, EditCardViewModel>() {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardEditBinding
        get() = FragmentCardEditBinding::inflate

    override val viewModel by viewModel<EditCardViewModel>()
    private val args by navArgs<EditCardFragmentArgs>()

    override fun prepareView(savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {
        binding.btnUpdate.setOnClickListener {
            viewModel.setEvent(EditCardEvent.OnUpdateButtonPress(getDataFromFields()))
        }
    }

    override fun renderState(state: EditCardState) {
        when (state.viewState) {
            ViewState.Idle -> viewModel.setEvent(EditCardEvent.OnCardLoad(args.id))
            ViewState.Loading -> Unit
            ViewState.Error -> Unit
            is ViewState.Success -> initCard(state.viewState.data)
        }
    }

    override fun renderEffect(effect: EditCardEffect) {
        when (effect) {
            is EditCardEffect.ShowError -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
            }
            EditCardEffect.NavigateUp -> {
                setFragmentResult(CARD_UPDATED_REQUEST_KEY, bundleOf(CARD_UPDATED_BUNDLE_KEY to true))
                findNavController().navigateUp()
            }
        }
    }

    private fun initCard(contact: ContactUi) = with(binding) {
        etFullName.setText(contact.name)
        etCompany.setText(contact.job)
        etPosition.setText(contact.position)
        etEmail.setText(contact.email)
        etTel.setText(contact.phoneMobile)
        etTelOffice.setText(contact.phoneOffice)
        clEdit.background.colorFilter = contact.getSrcInColorFilter()
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

    companion object {
        const val CARD_UPDATED_REQUEST_KEY = "card_updated_request_key"
        const val CARD_UPDATED_BUNDLE_KEY = "is_card_updated"
    }
}
