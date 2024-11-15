package com.musinsa.item.category.application.dto

import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.common.util.MessageUtil

data class CategoryServiceResponse(
    val name: String,
    val value: String,
) {
    companion object {
        fun of(category: Category) = CategoryServiceResponse(
            name = category.name,
            value = MessageUtil.getMessage(category.value),
        )
    }
}
