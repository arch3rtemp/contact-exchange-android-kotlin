package com.sweeftdigital.contactsexchange.domain.use_case

import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.domain.use_case.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class DeleteContactUseCase(private val repo: Repository) : BaseUseCase<Int, Flow<Unit>> {
    override suspend fun start(arg: Int?): Flow<Unit> {
        return repo.deleteContact(arg!!)
    }
}