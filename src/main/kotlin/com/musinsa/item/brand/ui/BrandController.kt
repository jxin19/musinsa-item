package com.musinsa.item.brand.ui

import com.musinsa.item.brand.application.BrandCommandService
import com.musinsa.item.brand.application.BrandQueryService
import com.musinsa.item.brand.ui.dto.BrandRequest
import com.musinsa.item.brand.ui.dto.BrandResponse
import com.musinsa.item.brand.ui.dto.BrandResponses
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "브랜드 관련 API")
@RestController
@RequestMapping("/brand")
class BrandController(
    private val brandCommandService: BrandCommandService,
    private val brandQueryService: BrandQueryService
) {

    /**
     * 브랜드 목록 조회하는 API
     *
     * @return 브랜드 목록 응답 객체
     */
    @Operation(summary = "브랜드 목록 조회하는 API")
    @GetMapping("/list")
    fun list(): BrandResponses {
        val brandServiceResponses = brandQueryService.getList()
        return BrandResponses.of(brandServiceResponses)
    }

    /**
     * 브랜드를 추가하는 API
     *
     * @param brandRequest 추가할 브랜드 정보를 담은
     * @return 추가된 브랜드 정보를 담은 응답 객체
     * @throws IllegalArgumentException 중복된 브랜드 정보가 입력된 경우
    */
    @Operation(summary = "브랜드를 추가하는 API")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody brandRequest: BrandRequest): BrandResponse {
        val brandServiceResponse = brandCommandService.create(brandRequest.toServiceDto())
        return BrandResponse.of(brandServiceResponse)
    }

    /**
     * 브랜드를 업데이트하는 API
     *
     * @param id 업데이트할 브랜드의 ID
     * @param brandRequest 업데이트할 브랜드 정보를 담은
     * @return 업데이트된 브랜드 정보를 담은 응답 객체
     * @throws EntityNotFoundException 해당 ID의 브랜드가 존재하지 않을 경우
     * @throws IllegalArgumentException 유효하지 않은 브랜드 정보가 입력된 경우
    */
    @Operation(summary = "브랜드를 업데이트하는 API")
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody brandRequest: BrandRequest
    ): BrandResponse {
        val brandServiceResponse = brandCommandService.update(id, brandRequest.toServiceDto())
        return BrandResponse.of(brandServiceResponse)
    }

    /**
     * 브랜드를 삭제하는 API
     *
     * @param id 삭제할 브랜드의 ID
     * @return 삭제 성공 시 빈 응답 객체
     * @throws EntityNotFoundException 해당 ID의 브랜드가 존재하지 않을 경우
     * @throws DataIntegrityViolationException 해당 브랜드와 연관된 데이터가 있어 삭제할 수 없는 경우
    */
    @Operation(summary = "브랜드를 삭제하는 API")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        brandCommandService.delete(id)
    }
}
