package com.musinsa.item.brand.application

import com.musinsa.item.brand.application.dto.BrandServiceResponses
import com.musinsa.item.brand.domain.Brand

interface BrandQueryService {
    fun validateDuplicateBrand(brand: Brand)
    fun getBrandById(id: Long): Brand
    fun getList(): BrandServiceResponses
}
