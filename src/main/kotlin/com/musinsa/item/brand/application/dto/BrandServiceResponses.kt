package com.musinsa.item.brand.application.dto

import com.musinsa.item.brand.domain.Brand

data class BrandServiceResponses(
    val list: List<BrandServiceResponse>
) {
    companion object {
        fun of(list: Iterable<Brand>) = BrandServiceResponses(list.map { BrandServiceResponse.of(it) })
    }
}
