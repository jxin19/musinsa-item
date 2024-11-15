package com.musinsa.item.item.repository.custom

import com.musinsa.item.item.domain.Item

interface ItemRepositoryCustom {
    fun fetchLowestRetailPricesAndBrandsByCategory(): List<Item>

    fun fetchBrandItemsForLowestRetailPrice(): List<Item>
}
