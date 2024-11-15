package com.musinsa.item.brand.application.dto

import com.musinsa.item.brand.domain.Brand

data class BrandServiceResponse(
    val id: Long,
    val name: String
) {
    companion object {
        fun of(brand: Brand) = BrandServiceResponse(
            id = brand.id,
            name = brand.nameValue
        )
    }
}
