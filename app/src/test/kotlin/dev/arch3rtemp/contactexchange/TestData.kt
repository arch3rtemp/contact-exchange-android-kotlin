package dev.arch3rtemp.contactexchange

import android.database.sqlite.SQLiteException
import dev.arch3rtemp.contactexchange.data.db.model.ContactEntity
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.presentation.model.ContactUi

object TestData {

    /**
     * Domain test data
     */
    const val NEGATIVE_CONTACT_ID = -1
    const val ZERO_CONTACT_ID = 0
    private const val SIMULATED_CREATED_TIME_1 = 1736190485327L
    private const val SIMULATED_CREATED_TIME_2 = 1736190485364L

    val testMyContact = Contact(
        id = 9,
        name = "John Doe",
        job = "Job",
        position = "Developer",
        email = "john@example.com",
        phoneMobile = "+15559879855",
        phoneOffice = "+15558797882",
        createdAt = SIMULATED_CREATED_TIME_1,
        color = 0xFF0000,
        isMy = true
    )

    val testScannedContact = Contact(
        id = 10,
        name = "Jane Doe",
        job = "JP Morgan",
        position = "CEO",
        email = "jane@example.com",
        phoneMobile = "+15559879853",
        phoneOffice = "+15558797884",
        createdAt = SIMULATED_CREATED_TIME_2,
        color = 0xFF00FF,
        isMy = false
    )

    val testNewContact = Contact(
        id = 0,
        name = "Justin Doe",
        job = "Microsoft",
        position = "COO",
        email = "justin@example.com",
        phoneMobile = "+15559879859",
        phoneOffice = "+15558797889",
        createdAt = -1,
        color = -1,
        isMy = true
    )

    val mergedContact = Contact(
        id = 9,
        name = "Justin Doe",
        job = "Microsoft",
        position = "COO",
        email = "justin@example.com",
        phoneMobile = "+15559879859",
        phoneOffice = "+15558797889",
        createdAt = SIMULATED_CREATED_TIME_1,
        color = 0xFF0000,
        isMy = true
    )

    val testContacts = listOf(testMyContact, testScannedContact)
    val sqlException = SQLiteException("Database error")

    /**
     * Data test data
     */
    val testMyContactEntity = ContactEntity(
        id = 9,
        name = "John Doe",
        job = "Job",
        position = "Developer",
        email = "john@example.com",
        phoneMobile = "+15559879855",
        phoneOffice = "+15558797882",
        createdAt = SIMULATED_CREATED_TIME_1,
        color = 0xFF0000,
        isMy = true
    )

    val testScannedContactEntity = ContactEntity(
        id = 10,
        name = "Jane Doe",
        job = "JP Morgan",
        position = "CEO",
        email = "jane@example.com",
        phoneMobile = "+15559879853",
        phoneOffice = "+15558797884",
        createdAt = SIMULATED_CREATED_TIME_2,
        color = 0xFF00FF,
        isMy = false
    )

    val mergedContactEntity = ContactEntity(
        id = 9,
        name = "Justin Doe",
        job = "Microsoft",
        position = "COO",
        email = "justin@example.com",
        phoneMobile = "+15559879859",
        phoneOffice = "+15558797889",
        createdAt = SIMULATED_CREATED_TIME_1,
        color = 0xFF0000,
        isMy = true
    )

    val testContactsEntity = listOf(testMyContactEntity, testScannedContactEntity)

    val testContactJson = """
        {
              "id": 9,
              "name": "John Doe",
              "job": "Job",
              "position": "Developer",
              "email": "john@example.com",
              "phoneMobile": "+15559879855",
              "phoneOffice": "+15558797882",
              "createdAt": 1736190485327,
              "formattedCreatedAt":"06 Jan 24",
              "color": 16711680,
              "isMy": true
            }
    """.trimIndent()

    /**
     * Presentation test data
     */
    val testMyContactUi = ContactUi(
        id = 9,
        name = "John Doe",
        job = "Job",
        position = "Developer",
        email = "john@example.com",
        phoneMobile = "+15559879855",
        phoneOffice = "+15558797882",
        createdAt = SIMULATED_CREATED_TIME_1,
        formattedCreatedAt = "06 Jan 25",
        color = 0xFF0000,
        isMy = true
    )

    val testScannedContactUi = ContactUi(
        id = 10,
        name = "Jane Doe",
        job = "JP Morgan",
        position = "CEO",
        email = "jane@example.com",
        phoneMobile = "+15559879853",
        phoneOffice = "+15558797884",
        createdAt = SIMULATED_CREATED_TIME_2,
        formattedCreatedAt = "06 Jan 25",
        color = 0xFF00FF,
        isMy = false
    )

    val testInvalidContactUi = ContactUi(
        id = NEGATIVE_CONTACT_ID,
        name = "Jane Doe",
        job = "JP Morgan",
        position = "CEO",
        email = "jane@example.com",
        phoneMobile = "+15559879853",
        phoneOffice = "+15558797884",
        createdAt = SIMULATED_CREATED_TIME_2,
        formattedCreatedAt = "06 Jan 25",
        color = 0xFF00FF,
        isMy = false
    )

    val testContactsUi = listOf(testMyContactUi, testScannedContactUi)

    val testContactUiJsonCompact = "{\"id\":9,\"name\":\"John Doe\",\"job\":\"Job\",\"position\":\"Developer\",\"email\":\"john@example.com\",\"phoneMobile\":\"+15559879855\",\"phoneOffice\":\"+15558797882\",\"createdAt\":1736190485327,\"formattedCreatedAt\":\"06 Jan 25\",\"color\":16711680,\"isMy\":true}"
}
