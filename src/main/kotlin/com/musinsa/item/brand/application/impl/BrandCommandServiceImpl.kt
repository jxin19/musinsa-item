package com.musinsa.item.brand.application.impl

import com.musinsa.item.brand.application.BrandCommandService
import com.musinsa.item.brand.application.BrandQueryService
import com.musinsa.item.brand.application.dto.BrandServiceRequest
import com.musinsa.item.brand.application.dto.BrandServiceResponse
import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.repository.BrandRepository
import com.musinsa.item.common.exception.DeleteConstraintException
import com.musinsa.item.common.util.MessageUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
class BrandCommandServiceImpl(
    private val brandRepository: BrandRepository,
    private val brandQueryService: BrandQueryService,
) : BrandCommandService {

    override fun create(brandServiceRequest: BrandServiceRequest): BrandServiceResponse {
        val brand: Brand = brandServiceRequest.toEntity()

        brandQueryService.validateDuplicateBrand(brand)

        val savedBrand: Brand = brandRepository.save(brand)

        return BrandServiceResponse.of(savedBrand)
    }

    override fun update(id: Long, brandServiceRequest: BrandServiceRequest): BrandServiceResponse {
        val brand: Brand = brandServiceRequest.toEntity()

        brandQueryService.validateDuplicateBrand(brand)

        val currentBrand: Brand = brandQueryService.getBrandById(id)

        currentBrand.updateName(brand.getName())

        return BrandServiceResponse.of(currentBrand)
    }

    override fun delete(id: Long) {
        val brand = brandQueryService.getBrandById(id)

        if (brand.hasItems()) {
            throw DeleteConstraintException(MessageUtil.getMessage("brand.error.exists.related.item"))
        }

        brandRepository.deleteById(id)
    }

}
