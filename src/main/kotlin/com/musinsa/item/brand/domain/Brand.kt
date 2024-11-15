package com.musinsa.item.brand.domain

import com.musinsa.item.brand.domain.property.BrandName
import com.musinsa.item.item.domain.Item
import jakarta.persistence.*

@Entity
@Table(name = "brand")
class Brand(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", length = 10, nullable = false, updatable = false, unique = true)
    val id: Long = 0,

    @Embedded
    private var name: BrandName? = null,

    @OneToMany(mappedBy = "brand", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val items: MutableList<Item> = mutableListOf()
) {

    val nameValue: String
        get() = name?.value ?: ""

    fun getName(): BrandName = name!!

    fun updateName(brandName: BrandName) {
        this.name = brandName
    }

    fun hasItems(): Boolean = items.isNotEmpty()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Brand

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "Brand(id=$id, name=$name)"
    }
}
