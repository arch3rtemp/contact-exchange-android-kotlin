package dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.viewholder

import dev.arch3rtemp.contactexchange.databinding.CardListItemBinding
import dev.arch3rtemp.contactexchange.presentation.model.CardUi
import dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.listener.CardClickListener
import dev.arch3rtemp.ui.view.listadapter.DefaultViewHolder

internal class CardViewHolder(
    binding: CardListItemBinding,
    private val listener: CardClickListener
) : DefaultViewHolder<CardListItemBinding, CardUi>(binding) {

    override fun onBind(item: CardUi) {
        super.onBind(item)

        binding.apply {
            tvCard.text = item.job
            tvCard.background.colorFilter = item.getSrcInColorFilter()

            root.setOnClickListener {
                listener.onCardClick(item.id)
            }
        }

    }
}
