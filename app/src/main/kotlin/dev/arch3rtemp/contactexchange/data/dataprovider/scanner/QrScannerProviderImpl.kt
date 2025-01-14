package dev.arch3rtemp.contactexchange.data.dataprovider.scanner

import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.data.mapper.JsonToContactMapper
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.ui.util.StringResourceManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class QrScannerProviderImpl(
    private val scanner: GmsBarcodeScanner,
    private val mapper: JsonToContactMapper,
    private val resourceManager: StringResourceManager
) : QrScannerProvider {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun scan(): Contact = suspendCancellableCoroutine { continuation ->
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                val contact = mapper.fromJson(requireNotNull(barcode.rawValue))
                continuation.resume(contact) { cause, _, _ -> (cause) }
            }
            .addOnCanceledListener {
                continuation.resumeWithException(CancellationException(resourceManager.string(R.string.msg_scan_cancelled)))
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
}
