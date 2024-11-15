package com.musinsa.item.item.application.dto

import com.musinsa.item.item.domain.Item
import java.math.BigDecimal

class ItemServiceResponses(
    val itemResponses: List<ItemServiceResponse>,
    val totalRetailPrice: BigDecimal
) {
    companion object {
        fun of(items: List<Item>) = ItemServiceResponses(
            itemResponses = items.map { ItemServiceResponse.of(it) },
            totalRetailPrice = items.sumOf { it.retailPriceValue }
        )
    }
}
