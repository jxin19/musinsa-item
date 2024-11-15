package com.musinsa.item.brand.ui.dto

import com.musinsa.item.brand.application.dto.BrandServiceRequest
import jakarta.validation.constraints.NotBlank

data class BrandRequest(
    @field:NotBlank(message = "{brand.error.empty.name}")
    val name: String
) {
    fun toServiceDto() = BrandServiceRequest(name)
}
