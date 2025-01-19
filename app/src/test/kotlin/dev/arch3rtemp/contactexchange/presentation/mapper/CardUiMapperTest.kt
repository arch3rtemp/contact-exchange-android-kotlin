package dev.arch3rtemp.contactexchange.presentation.mapper

import dev.arch3rtemp.contactexchange.TestData
import org.junit.Test
import kotlin.test.assertEquals

class CardUiMapperTest {

    private val mapper = CardUiMapper()

    @Test
    fun invokeToUiModel_returnsCardUi() {
        val card = mapper.toUiModel(TestData.testMyContact)

        assertEquals(TestData.testMyCardUi, card)
    }

    @Test
    fun invokeToUiList_returnsCardUiList() {
        val cards = mapper.toUiList(TestData.testContacts)

        assertEquals(TestData.testCardsUi, cards)
    }
}
