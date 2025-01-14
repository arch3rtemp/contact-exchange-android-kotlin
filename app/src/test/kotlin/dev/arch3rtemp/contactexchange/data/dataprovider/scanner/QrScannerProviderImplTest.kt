package dev.arch3rtemp.contactexchange.data.dataprovider.scanner

import com.google.android.gms.tasks.OnCanceledListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.data.mapper.JsonToContactMapper
import dev.arch3rtemp.ui.util.StringResourceManager
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class QrScannerProviderImplTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var mockMapper: JsonToContactMapper

    @MockK
    lateinit var mockResourceManager: StringResourceManager

    @RelaxedMockK
    lateinit var mockScanner: GmsBarcodeScanner

    @MockK
    lateinit var mockBarcodeTask: Task<Barcode>

    @MockK
    lateinit var mockBarcode: Barcode

    @InjectMockKs
    private lateinit var scannerProvider: QrScannerProviderImpl

    @Before
    fun setUp() {
        every { mockScanner.startScan() } returns mockBarcodeTask
    }

    @Test
    fun invokeScan_returnsValidContact() = runTest {
        every { mockBarcode.rawValue } returns TestData.testContactJson
        every { mockMapper.fromJson(TestData.testContactJson) } returns TestData.testScannedContact
        every { mockBarcodeTask.addOnSuccessListener(any()) } answers {
            // Capture the OnSuccessListener<Barcode> passed to addOnSuccessListener(...)
            val listener = firstArg<OnSuccessListener<Barcode>>()

            // Invoke onSuccess(...) to simulate success
            listener.onSuccess(mockBarcode)

            // Return the mock task
            mockBarcodeTask
        }
        every { mockBarcodeTask.addOnCanceledListener(any()) } returns mockBarcodeTask
        every { mockBarcodeTask.addOnFailureListener(any()) } returns mockBarcodeTask


        val contact = scannerProvider.scan()

        assertEquals(TestData.testScannedContact, contact)

        verify { mockScanner.startScan() }
        verify { mockBarcode.rawValue }
        verify { mockMapper.fromJson(TestData.testContactJson) }
    }

    @Test
    fun invokeScan_throwsCancellationExceptionOnCancel() = runTest {
        every { mockResourceManager.string(R.string.msg_scan_cancelled) } returns "Scan canceled"
        every { mockBarcodeTask.addOnSuccessListener(any()) } returns mockBarcodeTask
        every { mockBarcodeTask.addOnCanceledListener(any()) } answers {
            val listener = firstArg<OnCanceledListener>()
            listener.onCanceled()
            mockBarcodeTask
        }
        every { mockBarcodeTask.addOnFailureListener(any()) } returns mockBarcodeTask

        assertFailsWith(CancellationException::class) { scannerProvider.scan() }

        verify { mockScanner.startScan() }
        verify(exactly = 0) { mockBarcode.rawValue }
        verify(exactly = 0) { mockMapper.fromJson(TestData.testContactJson) }
    }

    @Test
    fun invokeScan_throwsExceptionOnFailure() = runTest {
        every { mockBarcodeTask.addOnSuccessListener(any()) } returns mockBarcodeTask
        every { mockBarcodeTask.addOnCanceledListener(any()) } returns mockBarcodeTask
        every { mockBarcodeTask.addOnFailureListener(any()) } answers {
            val listener = firstArg<OnFailureListener>()
            listener.onFailure(RuntimeException("Operation failure"))
            mockBarcodeTask
        }

        assertFailsWith<RuntimeException> { scannerProvider.scan() }

        verify { mockScanner.startScan() }
        verify(exactly = 0) { mockBarcode.rawValue }
        verify(exactly = 0) { mockMapper.fromJson(TestData.testContactJson) }
    }
}
