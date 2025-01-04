package dev.arch3rtemp.ui.view.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

interface RvDelegate<V : ViewBinding, I : RvItem> {

    fun isRelevantItem(item: RvItem): Boolean

    @LayoutRes
    fun layoutResId(): Int

    fun viewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): DefaultViewHolder<V, I>

    fun diffUtil(): DiffUtil.ItemCallback<I> = DefaultItemDiffCallback()
}
