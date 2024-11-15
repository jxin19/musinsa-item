package com.musinsa.item.item.ui.dto

import com.musinsa.item.item.application.dto.LowestHighestRetailPriceServiceResponse

class LowestHighestRetailPriceResponse(
    val 카테고리: String,
    val 최저가: ItemSummaryResponse,
    val 최고가: ItemSummaryResponse
) {
    companion object {
        fun of(lowestHighestRetailPriceServiceResponse: LowestHighestRetailPriceServiceResponse) = LowestHighestRetailPriceResponse(
            카테고리 = lowestHighestRetailPriceServiceResponse.category,
            최저가 = ItemSummaryResponse.ofBrandAndRetailPrice(lowestHighestRetailPriceServiceResponse.lowestRetailPriceItem),
            최고가 = ItemSummaryResponse.ofBrandAndRetailPrice(lowestHighestRetailPriceServiceResponse.highestRetailPriceItem)
        )
    }
}
