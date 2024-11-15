package com.musinsa.item.category.application

import com.musinsa.item.category.application.dto.CategoryServiceResponses

interface CategoryService {
    fun list(): CategoryServiceResponses
}
