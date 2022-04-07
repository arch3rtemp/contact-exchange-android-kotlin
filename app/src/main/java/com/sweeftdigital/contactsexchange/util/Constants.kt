package com.sweeftdigital.contactsexchange.util

object Constants {
    const val MY_CARD = 323
    const val NOT_MY_CARD = 324

    fun checkCardType(type: Int): Boolean {
        return when (type) {
            MY_CARD -> true
            NOT_MY_CARD -> false
            else -> throw IllegalArgumentException()
        }
    }
}