package dev.arch3rtemp.contactexchange.domain.repository

import dev.arch3rtemp.contactexchange.domain.model.Contact

interface ScannerRepository {
    suspend fun scan(): Contact
}
