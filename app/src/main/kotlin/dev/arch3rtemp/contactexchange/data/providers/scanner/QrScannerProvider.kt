package dev.arch3rtemp.contactexchange.data.providers.scanner

import dev.arch3rtemp.contactexchange.domain.model.Contact

interface QrScannerProvider {
    suspend fun scan(): Contact
}
