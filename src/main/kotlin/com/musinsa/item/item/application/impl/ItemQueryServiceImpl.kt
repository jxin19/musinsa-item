package com.musinsa.item.item.application.impl

import com.musinsa.item.brand.application.BrandQueryService
import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.common.util.MessageUtil
import com.musinsa.item.item.application.ItemQueryService
import com.musinsa.item.item.application.dto.BrandItemServiceResponses
import com.musinsa.item.item.application.dto.ItemServiceResponses
import com.musinsa.item.item.application.dto.LowestHighestRetailPriceServiceResponse
import com.musinsa.item.item.domain.Item
import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.item.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemQueryServiceImpl(
    private val itemRepository: ItemRepository,
    private val brandQueryService: BrandQueryService,
) : ItemQueryService {

    override fun getBrandById(brandId: Long): Brand =
        brandQueryService.getBrandById(brandId)

    override fun validateDuplicateItem(item: Item) {
        require(!itemRepository.existsByCategoryAndBrand(item.getCategory(), item.getBrand())) {
            MessageUtil.getMessage("item.error.already.exists")
        }
    }

    override fun getItemById(id: Long): Item =
        itemRepository.findById(id)
            .orElseThrow { NoSuchElementException(MessageUtil.getMessage("item.error.not.found")) }

    override fun getList(): ItemServiceResponses {
        val list: Iterable<Item> = itemRepository.findAll()
        return ItemServiceResponses.of(list.toList())
    }

    override fun getLowestRetailPricesAndBrandsByCategory(): ItemServiceResponses {
        val list: List<Item> = itemRepository.fetchLowestRetailPricesAndBrandsByCategory()
        return ItemServiceResponses.of(list)
    }

    override fun getBrandItemsForLowestRetailPrice(): BrandItemServiceResponses {
        val list: List<Item> = itemRepository.fetchBrandItemsForLowestRetailPrice()
        return BrandItemServiceResponses.of(list)
    }

    override fun getLowestHighestRetailPricesAndBrandByCategory(category: String): LowestHighestRetailPriceServiceResponse {
        val lowestRetailPriceItem: Item =
            itemRepository.findTopByCategoryOrderByRetailPriceAsc(Category.fromValue(category))
                .orElseThrow { NoSuchElementException(MessageUtil.getMessage("item.error.no.result")) }
        val highestRetailPriceItem: Item =
            itemRepository.findTopByCategoryOrderByRetailPriceDesc(Category.fromValue(category))
                .orElseThrow { NoSuchElementException(MessageUtil.getMessage("item.error.no.result")) }

        return LowestHighestRetailPriceServiceResponse.of(category, lowestRetailPriceItem, highestRetailPriceItem)
    }

}
