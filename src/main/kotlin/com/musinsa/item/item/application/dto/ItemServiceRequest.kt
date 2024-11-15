package com.musinsa.item.item.application.dto

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.item.domain.Item
import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.item.domain.property.RetailPrice
import java.math.BigDecimal

data class ItemServiceRequest(
    val category: String,
    val brandId: Long,
    val retailPrice: BigDecimal,
) {
    fun toEntity(brand: Brand) = Item(
        category = Category.fromValue(category),
        brand = brand,
        retailPrice = RetailPrice(retailPrice)
    )
}
