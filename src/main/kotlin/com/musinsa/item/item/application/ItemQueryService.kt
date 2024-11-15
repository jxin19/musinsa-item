package com.musinsa.item.item.application

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.item.application.dto.BrandItemServiceResponses
import com.musinsa.item.item.application.dto.ItemServiceResponses
import com.musinsa.item.item.application.dto.LowestHighestRetailPriceServiceResponse
import com.musinsa.item.item.domain.Item

interface ItemQueryService {
    fun getBrandById(brandId: Long): Brand

    fun validateDuplicateItem(item: Item)

    fun getItemById(id: Long): Item

    fun getList(): ItemServiceResponses

    fun getLowestRetailPricesAndBrandsByCategory(): ItemServiceResponses

    fun getBrandItemsForLowestRetailPrice(): BrandItemServiceResponses

    fun getLowestHighestRetailPricesAndBrandByCategory(category: String): LowestHighestRetailPriceServiceResponse
}
