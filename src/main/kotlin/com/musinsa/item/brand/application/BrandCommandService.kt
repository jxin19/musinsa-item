package com.musinsa.item.brand.application

import com.musinsa.item.brand.application.dto.BrandServiceRequest
import com.musinsa.item.brand.application.dto.BrandServiceResponse


interface BrandCommandService {
    fun create(brandServiceRequest: BrandServiceRequest): BrandServiceResponse

    fun update(id: Long, brandServiceRequest: BrandServiceRequest): BrandServiceResponse

    fun delete(id: Long)
}
