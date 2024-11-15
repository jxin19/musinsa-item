package com.musinsa.item.category.ui.dto

import com.musinsa.item.category.application.dto.CategoryServiceResponse

data class CategoryResponses(
    val list: List<CategoryResponse>
) {
    companion object {
        fun of(list: List<CategoryServiceResponse>) = CategoryResponses(
            list = list.map { CategoryResponse.of(it) }
        )
    }
}
