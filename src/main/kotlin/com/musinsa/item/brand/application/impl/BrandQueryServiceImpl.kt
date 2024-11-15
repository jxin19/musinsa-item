package com.musinsa.item.brand.application.impl

import com.musinsa.item.brand.application.BrandQueryService
import com.musinsa.item.brand.application.dto.BrandServiceResponses
import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.repository.BrandRepository
import com.musinsa.item.common.util.MessageUtil
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BrandQueryServiceImpl(
    private val brandRepository: BrandRepository
) : BrandQueryService {

    override fun validateDuplicateBrand(brand: Brand) {
        require(!brandRepository.existsByName(brand.getName())) {
            MessageUtil.getMessage("brand.error.already.exists")
        }
    }

    override fun getBrandById(id: Long): Brand =
        brandRepository.findById(id)
            .orElseThrow { EntityNotFoundException(MessageUtil.getMessage("brand.error.not.found")) }

    override fun getList(): BrandServiceResponses {
        val brands = brandRepository.findAll()
        return BrandServiceResponses.of(brands)
    }

}
