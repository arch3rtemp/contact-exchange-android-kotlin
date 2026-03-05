package dev.arch3rtemp.contactexchange.ui.view.listadapter

interface RvItem {

    val id: Int

    fun <T : RvItem> type(typeClass: Class<T>): T? =
        if (typeClass.isInstance(this)) typeClass.cast(this)
        else null
}
