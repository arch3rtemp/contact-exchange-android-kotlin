package dev.arch3rtemp.contactexchange.ui.view.listadapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DefaultItemDiffCallback<I : RvItem> : DiffUtil.ItemCallback<I>() {

    override fun areItemsTheSame(oldItem: I, newItem: I): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: I, newItem: I): Boolean {
        return oldItem == newItem
    }
}
