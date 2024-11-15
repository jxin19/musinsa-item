package com.musinsa.item.item

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.domain.property.BrandName
import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.item.application.dto.ItemServiceRequest
import com.musinsa.item.item.application.impl.ItemCommandServiceImpl
import com.musinsa.item.item.domain.Item
import com.musinsa.item.item.domain.property.RetailPrice
import com.musinsa.item.item.repository.ItemRepository
import com.musinsa.item.item.application.ItemCommandService
import com.musinsa.item.item.application.ItemQueryService
import com.musinsa.item.util.MessageSourceUtil
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

@ExtendWith(MessageSourceUtil::class)
class ItemCommandServiceTests {
    private lateinit var itemRepository: ItemRepository
    private lateinit var itemQueryService: ItemQueryService
    private lateinit var itemCommandService: ItemCommandService

    @BeforeEach
    fun setUp() {
        itemRepository = mockk()
        itemQueryService = mockk()
        itemCommandService = ItemCommandServiceImpl(itemRepository, itemQueryService)
    }

    @Test
    fun `상품_생성_성공`() {
        // given
        val brand = Brand(1, BrandName("나이키"))
        val request = ItemServiceRequest("상의", 1, BigDecimal("10000"))
        val savedItem = Item(1, Category.TOPS, RetailPrice(BigDecimal("10000")), brand)

        every { itemQueryService.getBrandById(1) } returns brand
        every { itemQueryService.validateDuplicateItem(any()) } just runs
        every { itemRepository.save(any()) } returns savedItem

        // when
        val response = itemCommandService.create(request)

        // then
        assertThat(response.id).isEqualTo(1)
        assertThat(response.category).isEqualTo("상의")
        assertThat(response.retailPrice).isEqualTo(BigDecimal("10000"))
        verify { itemRepository.save(any()) }
    }

    @Test
    fun `상품_수정_성공`() {
        // given
        val brand = Brand(1, BrandName("나이키"))
        val request = ItemServiceRequest("바지", 1, BigDecimal("20000"))
        val existingItem = Item(1, Category.TOPS, RetailPrice(BigDecimal("10000")), brand)

        every { itemQueryService.getBrandById(1) } returns brand
        every { itemQueryService.getItemById(1) } returns existingItem
        every { itemQueryService.validateDuplicateItem(any()) } just runs

        // when
        val response = itemCommandService.update(1, request)

        // then
        assertThat(response.category).isEqualTo("바지")
        assertThat(response.retailPrice).isEqualTo(BigDecimal("20000"))
    }

    @Test
    fun `상품_삭제_성공`() {
        // given
        val item = mockk<Item>()
        every { itemQueryService.getItemById(1) } returns item
        every { itemRepository.delete(any()) } just runs

        // when
        itemCommandService.delete(1)

        // then
        verify { itemRepository.delete(item) }
    }

}
