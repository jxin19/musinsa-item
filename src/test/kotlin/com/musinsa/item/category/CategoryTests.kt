package com.musinsa.item.category

import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.util.MessageSourceUtil
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MessageSourceUtil::class)
class CategoryTests {

    @Test
    fun `카테고리_이름으로_enum_조회_성공`() {
        // given
        val value = "상의"

        // when
        val category = Category.fromValue(value)

        // then
        assertThat(category).isEqualTo(Category.TOPS)
    }

    @ParameterizedTest
    @ValueSource(strings = ["상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리"])
    fun `모든_카테고리_이름으로_조회_성공`(value: String) {
        // when
        val category = Category.fromValue(value)

        // then
        assertThat(category).isNotNull
    }

    @Test
    fun `잘못된_카테고리_이름으로_조회시_예외발생`() {
        // given
        val invalidValue = "잘못된카테고리"

        // when & then
        assertThatThrownBy { Category.fromValue(invalidValue) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("올바른 카테고리를 입력해주세요.")
    }
}
