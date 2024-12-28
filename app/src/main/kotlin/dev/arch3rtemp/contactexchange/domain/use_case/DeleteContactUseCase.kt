package dev.arch3rtemp.contactexchange.domain.use_case

import dev.arch3rtemp.contactexchange.domain.repository.Repository
import dev.arch3rtemp.contactexchange.domain.use_case.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class DeleteContactUseCase(private val repo: Repository) : BaseUseCase<Int, Flow<Unit>> {
    override suspend fun start(arg: Int?): Flow<Unit> {
        return repo.deleteContact(arg!!)
    }
}
