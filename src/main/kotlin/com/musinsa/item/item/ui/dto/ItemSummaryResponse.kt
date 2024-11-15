package com.musinsa.item.item.ui.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.musinsa.item.item.application.dto.ItemServiceResponse
import com.musinsa.item.item.ui.extension.formatPrice

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ItemSummaryResponse(
    val 카테고리: String? = null,
    val 브랜드: String? = null,
    val 가격: String
) {
    companion object {
        fun of(itemServiceResponse: ItemServiceResponse) = ItemSummaryResponse(
            카테고리 = itemServiceResponse.category,
            브랜드 = itemServiceResponse.brandName,
            가격 = itemServiceResponse.retailPrice.formatPrice()
        )

        fun ofBrandAndRetailPrice(itemServiceResponse: ItemServiceResponse) = ItemSummaryResponse(
            브랜드 = itemServiceResponse.brandName,
            가격 = itemServiceResponse.retailPrice.formatPrice()
        )

        fun ofCategoryAndRetailPrice(itemServiceResponse: ItemServiceResponse) = ItemSummaryResponse(
            카테고리 = itemServiceResponse.category,
            가격 = itemServiceResponse.retailPrice.formatPrice()
        )
    }
}
