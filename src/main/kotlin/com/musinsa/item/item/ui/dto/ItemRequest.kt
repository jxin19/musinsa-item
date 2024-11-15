package com.musinsa.item.item.ui.dto

import com.musinsa.item.item.application.dto.ItemServiceRequest
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ItemRequest(
    @field:NotBlank(message = "{category.error.empty}")
    val category: String,

    @field:NotNull(message = "{brand.error.empty.name}")
    val brandId: Long,

    @field:NotNull(message = "{item.error.empty.retailprice}")
    val retailPrice: BigDecimal
) {
    fun toServiceDto() = ItemServiceRequest(
        category = category,
        brandId = brandId,
        retailPrice = retailPrice
    )
}
