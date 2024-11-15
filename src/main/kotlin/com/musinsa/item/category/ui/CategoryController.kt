package com.musinsa.item.category.ui

import com.musinsa.item.category.application.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "카테고리 관련 API")
@RestController
@RequestMapping("/category")
class CategoryController(
    private val categoryService: CategoryService
) {
    /**
     * 카테고리 목록을 조회하는 API
     *
     * @return 카테고리 응답 객체를 반환
     */
    @Operation(summary = "카테고리 목록을 조회하는 API")
    @GetMapping("/list")
    fun list() = categoryService.list()
}
