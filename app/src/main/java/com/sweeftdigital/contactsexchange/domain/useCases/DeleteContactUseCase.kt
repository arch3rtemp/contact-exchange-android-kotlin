package com.sweeftdigital.contactsexchange.domain.useCases

import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.domain.useCases.base.BaseUseCase

class DeleteContactUseCase(private val repo: Repository) : BaseUseCase<Int, Unit> {
    override suspend fun start(arg: Int?) {
        repo.deleteContact(arg!!)
    }
}