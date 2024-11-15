package com.musinsa.item.item.application.impl

import com.musinsa.item.item.application.ItemCommandService
import com.musinsa.item.item.application.ItemQueryService
import com.musinsa.item.item.application.dto.ItemServiceRequest
import com.musinsa.item.item.application.dto.ItemServiceResponse
import com.musinsa.item.item.domain.Item
import com.musinsa.item.item.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
class ItemCommandServiceImpl(
    private val itemRepository: ItemRepository,
    private val itemQueryService: ItemQueryService,
) : ItemCommandService {

    override fun create(itemServiceRequest: ItemServiceRequest): ItemServiceResponse {
        val brand = itemQueryService.getBrandById(itemServiceRequest.brandId)
        val item: Item = itemServiceRequest.toEntity(brand)

        itemQueryService.validateDuplicateItem(item)

        val savedItem: Item = itemRepository.save(item)

        return ItemServiceResponse.of(savedItem)
    }

    override fun update(id: Long, itemServiceRequest: ItemServiceRequest): ItemServiceResponse {
        val brand = itemQueryService.getBrandById(itemServiceRequest.brandId)
        val updateItem: Item = itemServiceRequest.toEntity(brand)
        val item: Item = itemQueryService.getItemById(id)

        item.isRequiredDuplicateValidation(updateItem)
            .takeIf { it }
            ?.let { itemQueryService.validateDuplicateItem(updateItem) }

        item.update(updateItem)

        return ItemServiceResponse.of(item)
    }

    override fun delete(id: Long) {
        val item: Item = itemQueryService.getItemById(id)
        itemRepository.delete(item)
    }

}
