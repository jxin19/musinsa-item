package com.musinsa.item.item.application.dto

import com.musinsa.item.item.domain.Item
import java.math.BigDecimal

class ItemServiceResponse(
    val id: Long,
    val category: String,
    val brandId: Long,
    val brandName: String,
    val retailPrice: BigDecimal
) {
    companion object {
        fun of(item: Item) = ItemServiceResponse(
            id = item.id,
            category = item.categoryValue,
            brandId = item.brandId,
            brandName = item.brandName,
            retailPrice = item.retailPriceValue
        )
    }
}
