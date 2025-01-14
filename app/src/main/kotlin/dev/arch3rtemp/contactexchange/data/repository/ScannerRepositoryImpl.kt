package dev.arch3rtemp.contactexchange.data.repository

import dev.arch3rtemp.contactexchange.data.dataprovider.scanner.QrScannerProvider
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.ScannerRepository

class ScannerRepositoryImpl(private val scanner: QrScannerProvider) : ScannerRepository {

    override suspend fun scan(): Contact {
        return scanner.scan()
    }
}
