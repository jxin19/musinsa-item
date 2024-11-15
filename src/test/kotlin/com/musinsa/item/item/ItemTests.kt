package com.musinsa.item.item

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.domain.property.BrandName
import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.item.domain.Item
import com.musinsa.item.item.domain.property.RetailPrice
import com.musinsa.item.util.MessageSourceUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

@ExtendWith(MessageSourceUtil::class)
class ItemTests {

    @Test
    fun `상품_생성_성공`() {
        // given
        val brand = Brand(name = BrandName("나이키"))
        val retailPrice = RetailPrice(BigDecimal("10000"))

        // when
        val item = Item(
            category = Category.TOPS,
            retailPrice = retailPrice,
            brand = brand
        )

        // then
        assertThat(item.categoryValue).isEqualTo("상의")
        assertThat(item.retailPriceValue).isEqualTo(BigDecimal("10000"))
        assertThat(item.brandName).isEqualTo("나이키")
    }

    @Test
    fun `상품_업데이트_성공`() {
        // given
        val originalBrand = Brand(name = BrandName("나이키"))
        val originalItem = Item(
            category = Category.TOPS,
            retailPrice = RetailPrice(BigDecimal("10000")),
            brand = originalBrand
        )

        val newBrand = Brand(name = BrandName("아디다스"))
        val updateItem = Item(
            category = Category.PANTS,
            retailPrice = RetailPrice(BigDecimal("20000")),
            brand = newBrand
        )

        // when
        originalItem.update(updateItem)

        // then
        assertThat(originalItem.categoryValue).isEqualTo("바지")
        assertThat(originalItem.retailPriceValue).isEqualTo(BigDecimal("20000"))
        assertThat(originalItem.brandName).isEqualTo("아디다스")
    }

    @Test
    fun `동일한_카테고리와_브랜드면_중복검증_불필요`() {
        // given
        val brand = Brand(name = BrandName("나이키"))
        val originalItem = Item(
            category = Category.TOPS,
            retailPrice = RetailPrice(BigDecimal("10000")),
            brand = brand
        )

        val updateItem = Item(
            category = Category.TOPS,
            retailPrice = RetailPrice(BigDecimal("20000")),
            brand = brand
        )

        // when & then
        assertThat(originalItem.isRequiredDuplicateValidation(updateItem)).isFalse
    }

    @Test
    fun `다른_카테고리나_브랜드면_중복검증_필요`() {
        // given
        val originalItem = Item(
            category = Category.TOPS,
            retailPrice = RetailPrice(BigDecimal("10000")),
            brand = Brand(name = BrandName("나이키"))
        )

        val updateItem = Item(
            category = Category.PANTS,
            retailPrice = RetailPrice(BigDecimal("20000")),
            brand = Brand(name = BrandName("아디다스"))
        )

        // when & then
        assertThat(originalItem.isRequiredDuplicateValidation(updateItem)).isTrue
    }

}
