package com.sweeftdigital.contactsexchange.domain.useCases

import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.domain.useCases.base.BaseUseCase

class SelectContactByIdUseCase(private val repo: Repository) : BaseUseCase<Int, Contact> {
    override fun start(arg: Int?): Contact {
        return repo.selectContactById(arg!!)
    }
}