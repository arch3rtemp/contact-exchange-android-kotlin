package dev.arch3rtemp.ui.view.listadapter

interface RvItem {
    fun <T : RvItem> type(typeClass: Class<T>): T? =
        if (typeClass.isInstance(this)) typeClass.cast(this)
        else null
}
