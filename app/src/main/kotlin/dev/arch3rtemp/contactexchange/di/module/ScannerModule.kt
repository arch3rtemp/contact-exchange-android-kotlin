package dev.arch3rtemp.contactexchange.di.module

import android.app.Activity
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dev.arch3rtemp.contactexchange.data.dataprovider.scanner.QrScannerProvider
import dev.arch3rtemp.contactexchange.data.dataprovider.scanner.QrScannerProviderImpl
import dev.arch3rtemp.contactexchange.data.repository.ScannerRepositoryImpl
import dev.arch3rtemp.contactexchange.domain.repository.ScannerRepository
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val SCANNER_MODULE = module {

    factory {
        GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .enableAutoZoom()
            .build()
    }

    factory { (activity: Activity) ->
        GmsBarcodeScanning.getClient(activity, get())
    }

    factory { (activity: Activity) ->
        QrScannerProviderImpl(get { parametersOf(activity) }, get(), get())
    } bind QrScannerProvider::class

    factory { (activity: Activity) ->
        ScannerRepositoryImpl(get { parametersOf(activity) })
    } bind ScannerRepository::class
}
