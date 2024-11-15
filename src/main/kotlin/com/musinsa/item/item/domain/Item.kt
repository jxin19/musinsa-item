package com.musinsa.item.item.domain

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.common.util.MessageUtil
import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.item.domain.property.RetailPrice
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "item",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_item_category_brand",
            columnNames = ["category", "brand_id"]
        )
    ]
)
class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", length = 10, nullable = false, updatable = false, unique = true)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private var category: Category,

    @Embedded
    private var retailPrice: RetailPrice
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private var brand: Brand? = null

    constructor(
        category: Category,
        retailPrice: RetailPrice,
        brand: Brand
    ) : this(0, category, retailPrice) {
        this.brand = brand
    }

    constructor(
        id: Long,
        category: Category,
        retailPrice: RetailPrice,
        brand: Brand
    ) : this(id, category, retailPrice) {
        this.brand = brand
    }

    fun isRequiredDuplicateValidation(updateItem: Item): Boolean =
        this.getCategory() != updateItem.getCategory() || this.brandId != updateItem.brandId

    fun update(item: Item) {
        this.category = item.getCategory()
        this.retailPrice = item.getRetailPrice()
        this.brand = item.getBrand()
    }

    fun getCategory(): Category = category

    private fun getRetailPrice(): RetailPrice = retailPrice

    fun getBrand(): Brand = brand!!

    val categoryValue: String
        get() = MessageUtil.getMessage(category.value)

    val retailPriceValue: BigDecimal
        get() = retailPrice.value

    val brandId: Long
        get() = brand?.id ?: 0

    val brandName: String
        get() = brand?.nameValue ?: ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false
        if (category != other.category) return false
        if (retailPrice != other.retailPrice) return false
        if (brand != other.brand) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + retailPrice.hashCode()
        result = 31 * result + (brand?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Item(id=$id, category=$category, retailPrice=$retailPrice, brand=$brand)"
    }
}
