package com.musinsa.item.item

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.domain.property.BrandName
import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.item.application.impl.ItemQueryServiceImpl
import com.musinsa.item.item.domain.Item
import com.musinsa.item.item.domain.property.RetailPrice
import com.musinsa.item.item.repository.ItemRepository
import com.musinsa.item.brand.application.BrandQueryService
import com.musinsa.item.item.application.ItemQueryService
import com.musinsa.item.util.MessageSourceUtil
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.*

@ExtendWith(MessageSourceUtil::class)
class ItemQueryServiceTests {
    private lateinit var itemRepository: ItemRepository
    private lateinit var brandQueryService: BrandQueryService
    private lateinit var itemQueryService: ItemQueryService

    @BeforeEach
    fun setUp() {
        itemRepository = mockk()
        brandQueryService = mockk()
        itemQueryService = ItemQueryServiceImpl(itemRepository, brandQueryService)
    }

    @Test
    fun `상품_중복_검증_성공`() {
        // given
        val brand = Brand(1, BrandName("나이키"))
        val item = Item(category = Category.TOPS, retailPrice = RetailPrice(BigDecimal("10000")), brand = brand)
        every { itemRepository.existsByCategoryAndBrand(any(), any()) } returns false

        // when & then
        itemQueryService.validateDuplicateItem(item)
        verify { itemRepository.existsByCategoryAndBrand(any(), any()) }
    }

    @Test
    fun `상품_중복시_예외발생`() {
        // given
        val brand = Brand(1, BrandName("나이키"))
        val item = Item(category = Category.TOPS, retailPrice = RetailPrice(BigDecimal("10000")), brand = brand)
        every { itemRepository.existsByCategoryAndBrand(any(), any()) } returns true

        // when & then
        assertThatThrownBy { itemQueryService.validateDuplicateItem(item) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품을 등록할 수 없습니다. 이미 등록된 카테고리와 브랜드입니다.")
    }

    @Test
    fun `카테고리별_최저가_최고가_조회_성공`() {
        // given
        val brand = Brand(1, BrandName("나이키"))
        val lowestItem = Item(1, Category.TOPS, RetailPrice(BigDecimal("10000")), brand)
        val highestItem = Item(2, Category.TOPS, RetailPrice(BigDecimal("20000")), brand)

        every { itemRepository.findTopByCategoryOrderByRetailPriceAsc(any()) } returns Optional.of(lowestItem)
        every { itemRepository.findTopByCategoryOrderByRetailPriceDesc(any()) } returns Optional.of(highestItem)

        // when
        val response = itemQueryService.getLowestHighestRetailPricesAndBrandByCategory("상의")

        // then
        assertThat(response.lowestRetailPriceItem.retailPrice).isEqualTo(BigDecimal("10000"))
        assertThat(response.highestRetailPriceItem.retailPrice).isEqualTo(BigDecimal("20000"))
    }

    @Test
    fun `카테고리별_최저가_브랜드_조회_성공`() {
        // given
        val brand = Brand(1, BrandName("나이키"))
        val items = listOf(
            Item(1, Category.TOPS, RetailPrice(BigDecimal("10000")), brand),
            Item(2, Category.PANTS, RetailPrice(BigDecimal("20000")), brand)
        )
        every { itemRepository.fetchLowestRetailPricesAndBrandsByCategory() } returns items

        // when
        val response = itemQueryService.getLowestRetailPricesAndBrandsByCategory()

        // then
        assertThat(response.itemResponses).hasSize(2)
        assertThat(response.itemResponses[0].retailPrice).isEqualTo(BigDecimal("10000"))
    }
}
