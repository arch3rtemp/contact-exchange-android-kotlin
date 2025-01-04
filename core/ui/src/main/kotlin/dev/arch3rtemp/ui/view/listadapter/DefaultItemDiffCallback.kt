package dev.arch3rtemp.ui.view.listadapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
//TODO optimize
class DefaultItemDiffCallback<I : RvItem> : DiffUtil.ItemCallback<I>() {

    override fun areItemsTheSame(oldItem: I, newItem: I): Boolean {
        return oldItem::class == newItem::class
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: I, newItem: I): Boolean {
        return oldItem == newItem
    }
}
