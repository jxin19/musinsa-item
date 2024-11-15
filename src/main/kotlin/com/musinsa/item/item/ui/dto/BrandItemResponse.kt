package com.musinsa.item.item.ui.dto

import com.musinsa.item.item.application.dto.BrandItemServiceResponses
import com.musinsa.item.item.ui.extension.formatPrice

data class BrandItemResponse(
    val 브랜드: String,
    val 카테고리: List<ItemSummaryResponse>,
    val 총액: String
) {
    companion object {
        fun of(brandItemServiceResponses: BrandItemServiceResponses) = BrandItemResponse(
            브랜드 = brandItemServiceResponses.brand,
            카테고리 = brandItemServiceResponses.itemResponses.map { ItemSummaryResponse.ofCategoryAndRetailPrice(it) },
            총액 = brandItemServiceResponses.totalRetailPrice.formatPrice()
        )
    }
}
