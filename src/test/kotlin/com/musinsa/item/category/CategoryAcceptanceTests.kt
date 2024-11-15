package com.musinsa.item.category

import com.musinsa.item.category.application.CategoryService
import com.musinsa.item.category.application.dto.CategoryServiceResponse
import com.musinsa.item.category.application.dto.CategoryServiceResponses
import com.musinsa.item.category.ui.CategoryController
import com.musinsa.item.util.MessageSourceUtil
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.mockito.BDDMockito.given

@WebMvcTest(CategoryController::class)
@ExtendWith(MessageSourceUtil::class)
class CategoryAcceptanceTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var categoryService: CategoryService

    @Test
    fun `카테고리_목록_조회_성공`() {
        // given
        val responses = CategoryServiceResponses(
            list = listOf(
                CategoryServiceResponse("TOPS", "상의"),
                CategoryServiceResponse("PANTS", "바지"),
                CategoryServiceResponse("SNEAKERS", "스니커즈")
            )
        )
        given(categoryService.list()).willReturn(responses)

        // when & then
        mockMvc.perform(
            get("/category/list")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list").isArray)
            .andExpect(jsonPath("$.list.length()").value(3))
            .andExpect(jsonPath("$.list[0].name").value("TOPS"))
            .andExpect(jsonPath("$.list[0].value").value("상의"))
            .andExpect(jsonPath("$.list[1].name").value("PANTS"))
            .andExpect(jsonPath("$.list[1].value").value("바지"))
            .andExpect(jsonPath("$.list[2].name").value("SNEAKERS"))
            .andExpect(jsonPath("$.list[2].value").value("스니커즈"))
    }

}
