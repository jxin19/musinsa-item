package com.musinsa.item.item.ui.dto

import com.musinsa.item.item.application.dto.ItemServiceResponse
import com.musinsa.item.item.ui.extension.formatPrice

data class ItemResponse(
    val id: Long,
    val category: String,
    val brandId: Long,
    val brandName: String,
    val retailPrice: String
) {
    companion object {
        fun of(itemServiceResponse: ItemServiceResponse) = ItemResponse(
            id = itemServiceResponse.id,
            category = itemServiceResponse.category,
            brandId = itemServiceResponse.brandId,
            brandName = itemServiceResponse.brandName,
            retailPrice = itemServiceResponse.retailPrice.formatPrice()
        )
    }
}
