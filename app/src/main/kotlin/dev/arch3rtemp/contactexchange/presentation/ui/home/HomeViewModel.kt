package dev.arch3rtemp.contactexchange.presentation.ui.home

import androidx.lifecycle.viewModelScope
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.usecase.DeleteContactUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.FilterContactsUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.GetMyCardsUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.GetScannedContactsUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.SaveContactUseCase
import dev.arch3rtemp.contactexchange.presentation.mapper.CardUiMapper
import dev.arch3rtemp.contactexchange.presentation.mapper.ContactUiMapper
import dev.arch3rtemp.contactexchange.presentation.model.ContactUi
import dev.arch3rtemp.ui.base.BaseViewModel
import dev.arch3rtemp.ui.util.ErrorMsgResolver
import dev.arch3rtemp.ui.util.StringResourceManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getMyCards: GetMyCardsUseCase,
    private val getScannedContacts: GetScannedContactsUseCase,
    private val deleteContact: DeleteContactUseCase,
    private val saveContact: SaveContactUseCase,
    private val filterContacts: FilterContactsUseCase,
    private val resourceManager: StringResourceManager,
    private val errorMsgResolver: ErrorMsgResolver,
    private val contactUiMapper: ContactUiMapper,
    private val cardUiMapper: CardUiMapper
) : BaseViewModel<HomeEvent, HomeEffect, HomeState>() {

    private var unfilteredContacts = listOf<Contact>()

    private val errorHandler = CoroutineExceptionHandler { context, throwable ->
        setEffect { HomeEffect.ShowError(errorMsgResolver.resolve(throwable.localizedMessage)) }
    }

    override fun createInitialState(): HomeState {
        return HomeState(cardsState = CardState.Idle, contactsState = ContactState.Idle)
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnContactsLoad -> {
                getContacts()
                subscribeToFilter()
            }
            HomeEvent.OnCardsLoad -> getCards()
            is HomeEvent.OnContactDelete -> deleteContact(event.contact)
            is HomeEvent.OnContactSaved -> saveContact(event.contact)
            is HomeEvent.OnSearchTyped -> filter(event.query, unfilteredContacts)
        }
    }

    private fun getCards() {
        viewModelScope.launch {
            getMyCards.invoke()
                .onStart { setState { copy(cardsState = CardState.Loading) } }
                .catch { error ->
                    setEffect { HomeEffect.ShowError(errorMsgResolver.resolve(error.localizedMessage)) }
                    setState { copy(cardsState = CardState.Error(resourceManager.string(R.string.msg_could_not_load_data))) }
                }
                .map { cards ->
                    cardUiMapper.toUiList(cards)
                }
                .collect { cardUiList ->
                    if (cardUiList.isNotEmpty()) {
                        setState { copy(cardsState = CardState.Success(cardUiList)) }
                    } else {
                        setState { copy(cardsState = CardState.Empty) }
                    }
                }
        }
    }

    private fun getContacts() {
        viewModelScope.launch {
            getScannedContacts.invoke()
                .onStart { setState { copy(contactsState = ContactState.Loading) } }
                .catch { error ->
                    setEffect { HomeEffect.ShowError(errorMsgResolver.resolve(error.localizedMessage)) }
                    setState { copy(contactsState = ContactState.Error(resourceManager.string(R.string.msg_could_not_load_data))) }
                }
                .map { contacts ->
                    unfilteredContacts = contacts
                    contactUiMapper.toUiList(contacts)
                }.collectLatest { contactUiList ->
                    if (contactUiList.isNotEmpty()) {
                        setState { copy(contactsState = ContactState.Success(contactUiList)) }
                    } else {
                        setState { copy(contactsState = ContactState.Empty) }
                    }
                }

        }
    }

    private fun saveContact(contact: Contact) {
        viewModelScope.launch(errorHandler) {
            saveContact.invoke(contact)
        }
    }

    private fun deleteContact(contact: ContactUi) {
        viewModelScope.launch(errorHandler) {
            deleteContact.invoke(contact.id)
            setEffect { HomeEffect.ShowUndo(contactUiMapper.fromUiModel(contact)) }
        }
    }

    private fun filter(query: String, unfiltered: List<Contact>) {
        viewModelScope.launch {
            filterContacts(query, unfiltered)
        }
    }

    private fun subscribeToFilter() {
        viewModelScope.launch {
            filterContacts.getFilteredContactsStream()
                .catch { error ->
                    setEffect { HomeEffect.ShowError(errorMsgResolver.resolve(error.localizedMessage)) }
                }
                .map { contacts ->
                    contactUiMapper.toUiList(contacts)
                }
                .collect { contacts ->
                    if (contacts.isEmpty()) {
                        setState { copy(contactsState = ContactState.Empty) }
                    } else {
                        setState { copy(contactsState = ContactState.Success((contacts))) }
                    }
                }
        }
    }
}
