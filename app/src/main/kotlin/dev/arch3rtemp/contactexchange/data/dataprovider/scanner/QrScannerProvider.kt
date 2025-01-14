package dev.arch3rtemp.contactexchange.data.dataprovider.scanner

import dev.arch3rtemp.contactexchange.domain.model.Contact

interface QrScannerProvider {
    suspend fun scan(): Contact
}
