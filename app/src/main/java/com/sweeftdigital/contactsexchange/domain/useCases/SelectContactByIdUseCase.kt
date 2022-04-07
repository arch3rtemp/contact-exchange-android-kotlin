package com.sweeftdigital.contactsexchange.domain.useCases

import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.domain.useCases.base.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class SelectContactByIdUseCase(private val repo: Repository) : BaseUseCase<Int, Flow<Contact>> {
    override suspend fun start(arg: Int?): Flow<Contact> {
        return repo.selectContactById(arg!!)
    }
}