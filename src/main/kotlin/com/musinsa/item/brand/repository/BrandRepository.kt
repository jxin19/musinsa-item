package com.musinsa.item.brand.repository

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.domain.property.BrandName
import org.springframework.data.repository.CrudRepository

interface BrandRepository : CrudRepository<Brand, Long> {
    fun existsByName(brandName: BrandName): Boolean
}
