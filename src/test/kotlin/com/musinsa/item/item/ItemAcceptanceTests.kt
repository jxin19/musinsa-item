package com.musinsa.item.item

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.domain.property.BrandName
import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.item.application.ItemCommandService
import com.musinsa.item.item.application.ItemQueryService
import com.musinsa.item.item.application.dto.*
import com.musinsa.item.item.domain.Item
import com.musinsa.item.item.domain.property.RetailPrice
import com.musinsa.item.item.ui.ItemController
import com.musinsa.item.item.ui.dto.ItemRequest
import com.musinsa.item.util.MessageSourceUtil
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@WebMvcTest(ItemController::class)
@ExtendWith(MessageSourceUtil::class)
class ItemAcceptanceTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var itemCommandService: ItemCommandService

    @MockBean
    private lateinit var itemQueryService: ItemQueryService

    private val nike = Brand(1, BrandName("나이키"))
    private val adidas = Brand(2, BrandName("아디다스"))

    @Test
    fun `상품_목록_조회_성공`() {
        // given
        val items = listOf(
            Item(1, Category.TOPS, RetailPrice(BigDecimal("10000")), nike),
            Item(2, Category.PANTS, RetailPrice(BigDecimal("20000")), adidas)
        )
        given(itemQueryService.getList()).willReturn(ItemServiceResponses.of(items))

        // when & then
        mockMvc.perform(get("/item/list"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list[0].id").value(1))
            .andExpect(jsonPath("$.list[0].category").value("상의"))
            .andExpect(jsonPath("$.list[0].retailPrice").value("10,000"))
    }

    @Test
    fun `카테고리별_최저가_브랜드_조회_성공`() {
        // given
        val items = listOf(
            Item(1, Category.TOPS, RetailPrice(BigDecimal("10000")), nike),
            Item(2, Category.PANTS, RetailPrice(BigDecimal("20000")), adidas)
        )
        given(itemQueryService.getLowestRetailPricesAndBrandsByCategory())
            .willReturn(ItemServiceResponses.of(items))

        // when & then
        mockMvc.perform(get("/item/lowest-retail-prices-and-brands-of-category"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.목록[0].카테고리").value("상의"))
            .andExpect(jsonPath("$.목록[0].브랜드").value("나이키"))
            .andExpect(jsonPath("$.목록[0].가격").value("10,000"))
    }

    @Test
    fun `단일_브랜드_최저가_상품_조회_성공`() {
        // given
        val items = listOf(
            Item(1, Category.TOPS, RetailPrice(BigDecimal("10000")), nike),
            Item(2, Category.PANTS, RetailPrice(BigDecimal("20000")), nike)
        )
        given(itemQueryService.getBrandItemsForLowestRetailPrice())
            .willReturn(BrandItemServiceResponses.of(items))

        // when & then
        mockMvc.perform(get("/item/brand-item-for-lowest-retail-prices"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.최저가.브랜드").value("나이키"))
            .andExpect(jsonPath("$.최저가.총액").value("30,000"))
    }

    @Test
    fun `카테고리별_최저최고가_조회_성공`() {
        // given
        val lowestItem = Item(1, Category.TOPS, RetailPrice(BigDecimal("10000")), nike)
        val highestItem = Item(2, Category.TOPS, RetailPrice(BigDecimal("20000")), adidas)

        given(itemQueryService.getLowestHighestRetailPricesAndBrandByCategory("상의"))
            .willReturn(LowestHighestRetailPriceServiceResponse.of("상의", lowestItem, highestItem))

        // when & then
        mockMvc.perform(get("/item/category/상의/lowest-highest-retail-price-and-brands"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.카테고리").value("상의"))
            .andExpect(jsonPath("$.최저가.브랜드").value("나이키"))
            .andExpect(jsonPath("$.최고가.브랜드").value("아디다스"))
    }

    @Test
    fun `상품_생성_성공`() {
        // given
        val request = ItemRequest("상의", 1, BigDecimal("10000"))
        val savedItem = Item(1, Category.TOPS, RetailPrice(BigDecimal("10000")), nike)
        given(itemCommandService.create(request.toServiceDto())).willReturn(ItemServiceResponse.of(savedItem))

        // when & then
        mockMvc.perform(
            post("/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.category").value("상의"))
    }

    @Test
    fun `상품_수정_성공`() {
        // given
        val request = ItemRequest("바지", 1, BigDecimal("20000"))
        val updatedItem = Item(1, Category.PANTS, RetailPrice(BigDecimal("20000")), nike)
        given(itemCommandService.update(1, request.toServiceDto())).willReturn(ItemServiceResponse.of(updatedItem))

        // when & then
        mockMvc.perform(
            put("/item/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.category").value("바지"))
    }

    @Test
    fun `상품_삭제_성공`() {
        // given
        willDoNothing().given(itemCommandService).delete(1)

        // when & then
        mockMvc.perform(delete("/item/1"))
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `상품_생성시_필수값_누락되면_실패`() {
        // given
        val request = ItemRequest("", 1, BigDecimal("10000"))

        // when & then
        mockMvc.perform(
            post("/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
    }
}
