package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.ScannerRepository

class ScanQrUseCase(private val repository: ScannerRepository) {

    suspend operator fun invoke(): Contact {
        return repository.scan()
    }
}
