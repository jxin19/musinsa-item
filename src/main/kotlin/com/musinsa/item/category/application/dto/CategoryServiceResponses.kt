package com.musinsa.item.category.application.dto

import com.musinsa.item.category.domain.property.Category

data class CategoryServiceResponses(
    val list: List<CategoryServiceResponse>,
) {
    companion object {
        fun of(list: Array<Category>) = CategoryServiceResponses(
            list = list.map { CategoryServiceResponse.of(it) }
        )
    }
}
