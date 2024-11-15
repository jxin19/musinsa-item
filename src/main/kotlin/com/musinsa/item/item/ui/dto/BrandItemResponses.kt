package com.musinsa.item.item.ui.dto

import com.musinsa.item.item.application.dto.BrandItemServiceResponses

data class BrandItemResponses(
    val 최저가: BrandItemResponse
) {
    companion object {
        fun of(brandItemServiceResponses: BrandItemServiceResponses) = BrandItemResponses(
            최저가 = BrandItemResponse.of(brandItemServiceResponses)
        )
    }
}
