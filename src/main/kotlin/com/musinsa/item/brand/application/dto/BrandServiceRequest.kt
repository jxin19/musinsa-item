package com.musinsa.item.brand.application.dto

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.domain.property.BrandName

data class BrandServiceRequest(
    val name: String
) {
    fun toEntity() = Brand(
        name = BrandName(name)
    )
}
