package dev.arch3rtemp.ui.util

import dev.arch3rtemp.ui.R

class ErrorMsgResolver(private val resourceManager: StringResourceManager) {

    fun resolve(msg: String?): String {
        return msg ?: resourceManager.string(R.string.msg_error_unspecified)
    }
}
