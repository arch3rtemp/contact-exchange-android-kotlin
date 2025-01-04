package dev.arch3rtemp.ui.view.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

class DefaultListAdapter(private val items: List<RvDelegate<*, *>>) :
    ListAdapter<RvItem, DefaultViewHolder<ViewBinding, RvItem>>(DefaultItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        val viewType = items.find { delegate -> delegate.isRelevantItem(getItem(position)) }
            ?.layoutResId()
            ?: error("View type not found: ${getItem(position)}")
        return viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DefaultViewHolder<ViewBinding, RvItem> {
        return items.find { result -> result.layoutResId() == viewType }
            ?.viewHolder(LayoutInflater.from(parent.context), parent)
            ?.let { result -> result as DefaultViewHolder<ViewBinding, RvItem> }
            ?: error("View type not found: $viewType")

    }

    override fun onBindViewHolder(
        holder: DefaultViewHolder<ViewBinding, RvItem>,
        position: Int
    ) = holder.onBind(getItem(position))

}
