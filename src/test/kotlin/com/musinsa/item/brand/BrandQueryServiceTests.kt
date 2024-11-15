package com.musinsa.item.brand

import com.musinsa.item.brand.application.BrandQueryService
import com.musinsa.item.brand.application.impl.BrandQueryServiceImpl
import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.domain.property.BrandName
import com.musinsa.item.brand.repository.BrandRepository
import com.musinsa.item.util.MessageSourceUtil
import io.mockk.*
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MessageSourceUtil::class)
class BrandQueryServiceTests {
    private lateinit var brandRepository: BrandRepository
    private lateinit var brandQueryService: BrandQueryService

    @BeforeEach
    fun setUp() {
        brandRepository = mockk()
        brandQueryService = BrandQueryServiceImpl(brandRepository)
    }

    @Test
    fun `브랜드명_중복_검증_성공`() {
        // given
        val brand = Brand(name = BrandName("나이키"))
        every { brandRepository.existsByName(any()) } returns false

        // when & then
        brandQueryService.validateDuplicateBrand(brand)
        verify { brandRepository.existsByName(any()) }
    }

    @Test
    fun `브랜드명_중복시_예외발생`() {
        // given
        val brand = Brand(name = BrandName("나이키"))
        every { brandRepository.existsByName(any()) } returns true

        // when & then
        assertThatThrownBy { brandQueryService.validateDuplicateBrand(brand) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("이미 등록된 브랜드입니다.")
    }

    @Test
    fun `존재하는_브랜드_조회_성공`() {
        // given
        val brand = Brand(1, BrandName("나이키"))
        every { brandRepository.findById(1) } returns Optional.of(brand)

        // when
        val found = brandQueryService.getBrandById(1)

        // then
        assertThat(found).isEqualTo(brand)
    }

    @Test
    fun `존재하지_않는_브랜드_조회시_예외발생`() {
        // given
        every { brandRepository.findById(1) } returns Optional.empty()

        // when & then
        assertThatThrownBy { brandQueryService.getBrandById(1) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage("존재하지 않는 브랜드입니다.")
    }

    @Test
    fun `브랜드_목록_조회_성공`() {
        // given
        val brands = listOf(
            Brand(1, BrandName("나이키")),
            Brand(2, BrandName("아디다스"))
        )
        every { brandRepository.findAll() } returns brands

        // when
        val response = brandQueryService.getList()

        // then
        assertThat(response.list).hasSize(2)
        assertThat(response.list).extracting("name")
            .containsExactly("나이키", "아디다스")
    }
}
