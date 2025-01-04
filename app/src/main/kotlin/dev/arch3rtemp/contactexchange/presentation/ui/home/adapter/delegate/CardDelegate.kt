package dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.databinding.CardListItemBinding
import dev.arch3rtemp.contactexchange.presentation.model.CardUi
import dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.listener.CardClickListener
import dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.viewholder.CardViewHolder
import dev.arch3rtemp.ui.view.listadapter.DefaultViewHolder
import dev.arch3rtemp.ui.view.listadapter.RvDelegate
import dev.arch3rtemp.ui.view.listadapter.RvItem

class CardDelegate(
    private val listener: CardClickListener
) : RvDelegate<CardListItemBinding, CardUi> {

    override fun isRelevantItem(item: RvItem): Boolean {
        return item is CardUi
    }

    override fun layoutResId(): Int {
        return R.layout.card_list_item
    }

    override fun viewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): DefaultViewHolder<CardListItemBinding, CardUi> {
        return CardViewHolder(CardListItemBinding.inflate(layoutInflater, parent, false), listener)
    }
}
