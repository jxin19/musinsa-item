package com.musinsa.item.category.application.impl

import com.musinsa.item.category.application.CategoryService
import com.musinsa.item.category.application.dto.CategoryServiceResponses
import com.musinsa.item.category.domain.property.Category
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl : CategoryService {
    override fun list() = CategoryServiceResponses.of(Category.entries.toTypedArray())
}
