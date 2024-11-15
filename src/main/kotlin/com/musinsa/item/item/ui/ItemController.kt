package com.musinsa.item.item.ui

import com.musinsa.item.item.application.ItemCommandService
import com.musinsa.item.item.application.ItemQueryService
import com.musinsa.item.item.ui.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "상품 관련 API")
@RestController
@RequestMapping("/item")
class ItemController(
    private val itemQueryService: ItemQueryService,
    private val itemCommandService: ItemCommandService
) {

    /**
     * 상품 목록 조회하는 API
     *
     * @return 상품 목록 응답 객체
     */
    @Operation(summary = "상품 목록 조회하는 API")
    @GetMapping("/list")
    fun list(): ItemResponses {
        val itemServiceResponses = itemQueryService.getList()
        return ItemResponses.of(itemServiceResponses)
    }

    /**
     * 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     *
     * @return 카테고리별 최저가격 정보를 담은 응답 객체
    */
    @Operation(summary = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API")
    @GetMapping("/lowest-retail-prices-and-brands-of-category")
    fun lowestRetailPricesAndBrandsOfCategory(): ItemSummaryResponses {
        val itemServiceResponses = itemQueryService.getLowestRetailPricesAndBrandsByCategory()
        return ItemSummaryResponses.of(itemServiceResponses)
    }

    /**
     * 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
     *
     * @return 최저가격 브랜드와 상품 정보를 담은 응답 객체
    */
    @Operation(summary = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
    @GetMapping("/brand-item-for-lowest-retail-prices")
    fun brandItemsForLowestRetailPrice(): BrandItemResponses {
        val brandItemServiceResponses = itemQueryService.getBrandItemsForLowestRetailPrice()
        return BrandItemResponses.of(brandItemServiceResponses)
    }

    /**
     * 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
     *
     * @param category 조회할 카테고리 이름
     * @return 최저/최고 가격 정보를 담은 응답 객체
     * @throws IllegalArgumentException 유효하지 않은 카테고리 이름이 입력된 경우
     * @throws NoSuchElementException 조회할 데이터가 없는 경우
    */
    @Operation(summary = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API")
    @GetMapping("/category/{category}/lowest-highest-retail-price-and-brands")
    fun getLowestHighestRetailPricesAndBrandByCategory(@PathVariable category: String): LowestHighestRetailPriceResponse {
        val lowestHighestRetailPriceServiceResponse = itemQueryService.getLowestHighestRetailPricesAndBrandByCategory(category)
        return LowestHighestRetailPriceResponse.of(lowestHighestRetailPriceServiceResponse)
    }

    /**
     * 상품을 추가하는 API
     *
     * @param itemRequest 추가할 상품 정보를 담은 DTO
     * @return 추가된 상품 정보를 담은 응답 객체
     * @throws IllegalArgumentException 유효하지 않은 상품 정보가 입력된 경우
    */
    @Operation(summary = "상품을 추가하는 API")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody itemRequest: ItemRequest): ItemResponse {
        val itemServiceResponse = itemCommandService.create(itemRequest.toServiceDto())
        return ItemResponse.of(itemServiceResponse)
    }

    /**
     * 상품을 업데이트하는 API
     *
     * @param id 업데이트할 상품의 ID
     * @param itemRequest 업데이트할 상품 정보를 담은 DTO
     * @return 업데이트된 상품 정보를 담은 응답 객체
     * @throws IllegalArgumentException 유효하지 않은 상품 정보가 입력된 경우
     * @throws NoSuchElementException 해당 ID의 상품이 존재하지 않을 경우
    */
    @Operation(summary = "상품을 업데이트하는 API")
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody itemRequest: ItemRequest
    ): ItemResponse {
        val itemServiceResponse = itemCommandService.update(id, itemRequest.toServiceDto())
        return ItemResponse.of(itemServiceResponse)
    }

    /**
     * 상품을 삭제하는 API
     *
     * @param id 삭제할 상품의 ID
     * @throws NoSuchElementException 해당 ID의 상품이 존재하지 않을 경우
    */
    @Operation(summary = "상품을 삭제하는 API")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        itemCommandService.delete(id)
    }

}
