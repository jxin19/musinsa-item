package com.musinsa.item.item.repository

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.item.domain.Item
import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.item.repository.custom.ItemRepositoryCustom
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface ItemRepository : CrudRepository<Item, Long>, ItemRepositoryCustom {
    fun existsByCategoryAndBrand(category: Category, brand: Brand): Boolean

    @EntityGraph(attributePaths = ["brand"])
    override fun findById(id: Long): Optional<Item>

    @EntityGraph(attributePaths = ["brand"])
    override fun findAll(): Iterable<Item>

    override fun fetchLowestRetailPricesAndBrandsByCategory(): List<Item>

    override fun fetchBrandItemsForLowestRetailPrice(): List<Item>

    @EntityGraph(attributePaths = ["brand"])
    fun findTopByCategoryOrderByRetailPriceDesc(category: Category): Optional<Item>

    @EntityGraph(attributePaths = ["brand"])
    fun findTopByCategoryOrderByRetailPriceAsc(category: Category): Optional<Item>
}
