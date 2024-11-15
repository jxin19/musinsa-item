package com.musinsa.item.item.ui.dto

import com.musinsa.item.item.application.dto.ItemServiceResponses

data class ItemResponses(
    val list: List<ItemResponse>
) {
    companion object {
        fun of(itemServiceResponses: ItemServiceResponses) = ItemResponses(
            list = itemServiceResponses.itemResponses.map { ItemResponse.of(it) }
        )
    }
}
