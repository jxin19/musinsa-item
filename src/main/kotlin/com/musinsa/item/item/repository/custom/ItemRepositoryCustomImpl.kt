package com.musinsa.item.item.repository.custom

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.domain.property.BrandName
import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.item.domain.Item
import com.musinsa.item.item.domain.QItem
import com.musinsa.item.item.domain.property.RetailPrice
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Path
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import java.math.BigDecimal

class ItemRepositoryCustomImpl(
    private val em: EntityManager,
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(em)
) : ItemRepositoryCustom {

    private val qItem = QItem.item

    override fun fetchLowestRetailPricesAndBrandsByCategory(): List<Item> {
        val results = em.createNativeQuery("""
            WITH ranked_items AS (
                SELECT a.item_id,
                       a.category,
                       a.retail_price,
                       a.brand_id,
                       b.name as brand_name,
                       ROW_NUMBER() OVER (
                           PARTITION BY a.category 
                           ORDER BY a.retail_price ASC, a.brand_id DESC
                       ) as rn
                FROM item a
                LEFT JOIN brand b ON b.brand_id = a.brand_id
            )
            SELECT item_id, category, retail_price, brand_id, brand_name
            FROM ranked_items 
            WHERE rn = 1
        """.trimIndent())
            .resultList as List<Array<Any>>

        return results.map { row ->
            Item(
                id = (row[0] as Number).toLong(),
                category = Category.valueOf(row[1] as String),
                retailPrice = RetailPrice(row[2] as BigDecimal),
                brand = Brand(
                    id = (row[3] as Number).toLong(),
                    name = BrandName(row[4] as String)
                )
            )
        }
    }

    override fun fetchBrandItemsForLowestRetailPrice(): List<Item> {
        val totalRetailPrice: Path<String> = Expressions.path(String::class.java, "totalRetailPrice")
        val brandTotalPrices = queryFactory.select(
            qItem.brand,
            qItem.retailPrice._value.sum().`as`(totalRetailPrice.toString())
        )
            .from(qItem)
            .groupBy(qItem.brand)
            .orderBy(OrderSpecifier(Order.ASC, totalRetailPrice))
            .limit(1)
            .fetchOne()

        return queryFactory.select(
            Projections.constructor(
                Item::class.java,
                qItem.id,
                qItem.category,
                Projections.constructor(
                    RetailPrice::class.java,
                    qItem.retailPrice._value.min().coalesce(BigDecimal.ZERO)
                ),
                qItem.brand
            )
        )
            .from(qItem)
            .where(qItem.brand.eq(brandTotalPrices?.get(qItem.brand)))
            .groupBy(qItem.id)
            .fetch()
    }

}
