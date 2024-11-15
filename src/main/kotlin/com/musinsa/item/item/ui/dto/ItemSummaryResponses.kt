package com.musinsa.item.item.ui.dto

import com.musinsa.item.item.application.dto.ItemServiceResponses
import com.musinsa.item.item.ui.extension.formatPrice

data class ItemSummaryResponses(
    val 목록: List<ItemSummaryResponse>,
    val 총액: String
) {
    companion object {
        fun of(itemServiceResponses: ItemServiceResponses) = ItemSummaryResponses(
            목록 = itemServiceResponses.itemResponses.map { ItemSummaryResponse.of(it) },
            총액 = itemServiceResponses.totalRetailPrice.formatPrice()
        )
    }
}
