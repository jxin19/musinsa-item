package com.musinsa.item.brand.ui.dto

import com.musinsa.item.brand.application.dto.BrandServiceResponses

data class BrandResponses(
    val list: List<BrandResponse>
) {
    companion object {
        fun of(brandServiceResponses: BrandServiceResponses) =
            BrandResponses(brandServiceResponses.list.map { BrandResponse.of(it) })
    }
}
