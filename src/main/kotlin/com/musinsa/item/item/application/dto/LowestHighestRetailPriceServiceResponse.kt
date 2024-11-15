package com.musinsa.item.item.application.dto

import com.musinsa.item.item.domain.Item

class LowestHighestRetailPriceServiceResponse(
    val category: String,
    val lowestRetailPriceItem: ItemServiceResponse,
    val highestRetailPriceItem: ItemServiceResponse
) {
    companion object {
        fun of(
            category: String,
            lowestRetailPriceItem: Item,
            highestRetailPriceItem: Item
        ) = LowestHighestRetailPriceServiceResponse(
            category = category,
            lowestRetailPriceItem = ItemServiceResponse.of(lowestRetailPriceItem),
            highestRetailPriceItem = ItemServiceResponse.of(highestRetailPriceItem)
        )
    }
}
