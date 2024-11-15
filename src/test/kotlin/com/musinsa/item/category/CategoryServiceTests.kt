package com.musinsa.item.category

import com.musinsa.item.category.application.CategoryService
import com.musinsa.item.category.application.impl.CategoryServiceImpl
import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.util.MessageSourceUtil
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import com.musinsa.item.common.util.MessageUtil

@ExtendWith(MessageSourceUtil::class)
class CategoryServiceTests {

    private lateinit var categoryService: CategoryService

    @BeforeEach
    fun setUp() {
        categoryService = CategoryServiceImpl()
        mockkObject(MessageUtil.Companion)
        mockMessageResponses()
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(MessageUtil.Companion)
    }

    @Test
    fun `전체_카테고리_조회_성공`() {
        // when
        val responses = categoryService.list()

        // then
        assertThat(responses.list).hasSize(8)
        assertThat(responses.list).extracting("name")
            .containsExactly(
                "TOPS", "OUTERWEAR", "PANTS", "SNEAKERS",
                "BAGS", "HATS", "SOCKS", "ACCESSORIES"
            )
        assertThat(responses.list).extracting("value")
            .containsExactly(
                "상의", "아우터", "바지", "스니커즈",
                "가방", "모자", "양말", "액세서리"
            )
    }

    private fun mockMessageResponses() {
        Category.entries.forEach { category ->
            every { MessageUtil.getMessage(category.value) } returns when (category) {
                Category.TOPS -> "상의"
                Category.OUTERWEAR -> "아우터"
                Category.PANTS -> "바지"
                Category.SNEAKERS -> "스니커즈"
                Category.BAGS -> "가방"
                Category.HATS -> "모자"
                Category.SOCKS -> "양말"
                Category.ACCESSORIES -> "액세서리"
            }
        }
    }
}
