package com.musinsa.item.brand

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.item.brand.application.BrandCommandService
import com.musinsa.item.brand.application.BrandQueryService
import com.musinsa.item.brand.application.dto.BrandServiceResponse
import com.musinsa.item.brand.application.dto.BrandServiceResponses
import com.musinsa.item.brand.ui.BrandController
import com.musinsa.item.brand.ui.dto.BrandRequest
import com.musinsa.item.common.exception.DeleteConstraintException
import com.musinsa.item.util.MessageSourceUtil
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
import org.mockito.BDDMockito.willThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BrandController::class)
@ExtendWith(MessageSourceUtil::class)
class BrandAcceptanceTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var brandCommandService: BrandCommandService

    @MockBean
    private lateinit var brandQueryService: BrandQueryService

    @Test
    fun `브랜드_목록_조회_성공`() {
        // given
        val responses = BrandServiceResponses(
            list = listOf(
                BrandServiceResponse(1, "나이키"),
                BrandServiceResponse(2, "아디다스")
            )
        )
        given(brandQueryService.getList()).willReturn(responses)

        // when & then
        mockMvc.perform(get("/brand/list"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list[0].id").value(1))
            .andExpect(jsonPath("$.list[0].name").value("나이키"))
            .andExpect(jsonPath("$.list[1].id").value(2))
            .andExpect(jsonPath("$.list[1].name").value("아디다스"))
    }

    @Test
    fun `브랜드_생성_성공`() {
        // given
        val request = BrandRequest("나이키")
        given(brandCommandService.create(request.toServiceDto()))
            .willReturn(BrandServiceResponse(1, "나이키"))

        // when & then
        mockMvc.perform(
            post("/brand")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("나이키"))
    }

    @Test
    fun `브랜드_생성시_이름_누락되면_실패`() {
        // given
        val request = BrandRequest("")

        // when & then
        mockMvc.perform(
            post("/brand")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `브랜드_수정_성공`() {
        // given
        val request = BrandRequest("아디다스")
        given(brandCommandService.update(1, request.toServiceDto()))
            .willReturn(BrandServiceResponse(1, "아디다스"))

        // when & then
        mockMvc.perform(
            put("/brand/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("아디다스"))
    }

    @Test
    fun `존재하지_않는_브랜드_수정시_실패`() {
        // given
        val request = BrandRequest("아디다스")
        given(brandCommandService.update(999L, request.toServiceDto()))
            .willThrow(EntityNotFoundException("존재하지 않는 브랜드입니다."))

        // when & then
        mockMvc.perform(
            put("/brand/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun `브랜드_삭제_성공`() {
        // given
        willDoNothing().given(brandCommandService).delete(1)

        // when & then
        mockMvc.perform(delete("/brand/1"))
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `연관된_상품이_있는_브랜드_삭제시_실패`() {
        // given
        willThrow(DeleteConstraintException("해당 브랜드에 연관된 상품이 존재하여 삭제할 수 없습니다."))
            .given(brandCommandService).delete(1)

        // when & then
        mockMvc.perform(delete("/brand/1"))
            .andDo(print())
            .andExpect(status().isConflict)
    }
}
