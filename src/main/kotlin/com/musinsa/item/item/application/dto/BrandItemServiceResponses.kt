package com.musinsa.item.item.application.dto

import com.musinsa.item.item.domain.Item
import java.math.BigDecimal

class BrandItemServiceResponses(
    var brand: String,
    var itemResponses: List<ItemServiceResponse>,
    var totalRetailPrice: BigDecimal
) {
    companion object {
        fun of(items: List<Item>) = BrandItemServiceResponses(
            brand = items[0].brandName,
            itemResponses = items.map { ItemServiceResponse.of(it) },
            totalRetailPrice = items.sumOf { it.retailPriceValue }
        )
    }
}
