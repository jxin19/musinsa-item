package com.musinsa.item.brand

import com.musinsa.item.brand.application.BrandCommandService
import com.musinsa.item.brand.application.BrandQueryService
import com.musinsa.item.brand.application.dto.BrandServiceRequest
import com.musinsa.item.brand.application.impl.BrandCommandServiceImpl
import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.domain.property.BrandName
import com.musinsa.item.brand.repository.BrandRepository
import com.musinsa.item.common.exception.DeleteConstraintException
import com.musinsa.item.util.MessageSourceUtil
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MessageSourceUtil::class)
class BrandCommandServiceTests {
    private lateinit var brandRepository: BrandRepository
    private lateinit var brandQueryService: BrandQueryService
    private lateinit var brandCommandService: BrandCommandService

    @BeforeEach
    fun setUp() {
        brandRepository = mockk()
        brandQueryService = mockk()
        brandCommandService = BrandCommandServiceImpl(brandRepository, brandQueryService)
    }

    @Test
    fun `브랜드_생성_성공`() {
        // given
        val request = BrandServiceRequest("나이키")
        val savedBrand = Brand(1, BrandName("나이키"))

        every { brandQueryService.validateDuplicateBrand(any()) } just runs
        every { brandRepository.save(any()) } returns savedBrand

        // when
        val response = brandCommandService.create(request)

        // then
        assertThat(response.id).isEqualTo(1)
        assertThat(response.name).isEqualTo("나이키")
        verify { brandQueryService.validateDuplicateBrand(any()) }
        verify { brandRepository.save(any()) }
    }

    @Test
    fun `브랜드_수정_성공`() {
        // given
        val request = BrandServiceRequest("아디다스")
        val existingBrand = Brand(1, BrandName("나이키"))

        every { brandQueryService.validateDuplicateBrand(any()) } just runs
        every { brandQueryService.getBrandById(1) } returns existingBrand

        // when
        val response = brandCommandService.update(1, request)

        // then
        assertThat(response.name).isEqualTo("아디다스")
        verify { brandQueryService.validateDuplicateBrand(any()) }
        verify { brandQueryService.getBrandById(1) }
    }

    @Test
    fun `연관된_상품이_있으면_브랜드_삭제_실패`() {
        // given
        val brand = mockk<Brand>()
        every { brandQueryService.getBrandById(1) } returns brand
        every { brand.hasItems() } returns true

        // when & then
        assertThatThrownBy { brandCommandService.delete(1) }
            .isInstanceOf(DeleteConstraintException::class.java)
            .hasMessage("해당 브랜드에 연관된 상품이 존재하여 삭제할 수 없습니다.")
    }

    @Test
    fun `브랜드_삭제_성공`() {
        // given
        val brand = mockk<Brand>()
        every { brandQueryService.getBrandById(1) } returns brand
        every { brand.hasItems() } returns false
        every { brandRepository.deleteById(1) } just runs

        // when & then
        brandCommandService.delete(1)
        verify { brandRepository.deleteById(1) }
    }
}
