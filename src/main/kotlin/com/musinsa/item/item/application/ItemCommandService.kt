package com.musinsa.item.item.application

import com.musinsa.item.item.application.dto.ItemServiceRequest
import com.musinsa.item.item.application.dto.ItemServiceResponse

interface ItemCommandService {
    fun create(itemServiceRequest: ItemServiceRequest): ItemServiceResponse

    fun update(id: Long, itemServiceRequest: ItemServiceRequest): ItemServiceResponse

    fun delete(id: Long)
}
