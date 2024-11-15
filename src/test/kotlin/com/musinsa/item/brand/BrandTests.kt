package com.musinsa.item.brand

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.domain.property.BrandName
import com.musinsa.item.util.MessageSourceUtil
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MessageSourceUtil::class)
class BrandTests {

    @Test
    fun `브랜드_생성_성공`() {
        // given
        val brandName = BrandName("나이키")

        // when
        val brand = Brand(name = brandName)

        // then
        assertThat(brand.nameValue).isEqualTo("나이키")
    }

    @Test
    fun `브랜드명_수정_성공`() {
        // given
        val brand = Brand(name = BrandName("나이키"))
        val newBrandName = BrandName("아디다스")

        // when
        brand.updateName(newBrandName)

        // then
        assertThat(brand.nameValue).isEqualTo("아디다스")
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `브랜드명이_빈값이면_예외발생`(emptyName: String) {
        assertThatThrownBy { BrandName(emptyName) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("브랜드명은 1글자 이상으로 입력해 주세요.")
    }

    @Test
    fun `브랜드명이_100자_초과하면_예외발생`() {
        // given
        val longName = "a".repeat(101)

        // when & then
        assertThatThrownBy { BrandName(longName) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("브랜드명은 1~100글자 이내로 입력해 주세요.")
    }

    @Test
    fun `연관된_상품이_없으면_false_반환`() {
        // given
        val brand = Brand(name = BrandName("나이키"))

        // when & then
        assertThat(brand.hasItems()).isFalse()
    }
}
