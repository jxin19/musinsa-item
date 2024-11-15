package com.musinsa.item.brand.ui.dto

import com.musinsa.item.brand.application.dto.BrandServiceResponse

data class BrandResponse(
    val id: Long,
    val name: String
) {
    companion object {
        fun of(brandServiceResponse: BrandServiceResponse) = BrandResponse(
            id = brandServiceResponse.id,
            name = brandServiceResponse.name
        )
    }
}
