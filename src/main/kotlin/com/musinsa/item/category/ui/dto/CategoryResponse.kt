package com.musinsa.item.category.ui.dto

import com.musinsa.item.category.application.dto.CategoryServiceResponse

data class CategoryResponse(
    val name: String,
    val value: String,
) {
    companion object {
        fun of(categoryServiceResponse: CategoryServiceResponse) = CategoryResponse(
            name = categoryServiceResponse.name,
            value = categoryServiceResponse.value,
        )
    }
}
