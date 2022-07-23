package com.sweeftdigital.contactsexchange.presentation.main.edit

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sweeftdigital.contactsexchange.databinding.FragmentCardEditBinding
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.presentation.base.BaseFragment
import com.sweeftdigital.contactsexchange.util.NavControllerStateHandle
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditCardFragment : BaseFragment<EditCardEvent, EditCardEffect, EditCardState, FragmentCardEditBinding, EditCardViewModel>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardEditBinding
        get() = FragmentCardEditBinding::inflate

    override val viewModel by viewModel<EditCardViewModel>()
    private val arg by navArgs<EditCardFragmentArgs>()

    override fun prepareView(savedInstanceState: Bundle?) {
        if (viewModel.currentState?.viewState is ViewState.Idle) {
            viewModel.setEvent(EditCardEvent.OnCardLoaded(arg.id))
        }
        setListeners()
    }

    private fun setListeners() {
        binding.btnSave.setOnClickListener {
            viewModel.setEvent(EditCardEvent.OnSaveButtonPressed(getDataFromFields()))
            setInfoAboutSaveClick()
        }
    }

    private fun setInfoAboutSaveClick() {
        NavControllerStateHandle<Boolean>(findNavController())
            .savedPreviousBackStackEntry("saveButtonClicked", true)
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
        val currentContact = viewModel.currentState!!.contact
        return Contact(
            id = currentContact.id,
            name = etFullName.text.toString(),
            job = etCompany.text.toString(),
            position = etPosition.text.toString(),
            email = etEmail.text.toString(),
            phoneMobile = etTel.text.toString(),
            phoneOffice = etTelOffice.text.toString(),
            createDate = currentContact.createDate,
            color = currentContact.color,
            isMy = currentContact.isMy
        )
    }

    override fun renderState(state: EditCardState) {
        initCard(state.contact)
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