package com.musinsa.item.category.domain.property

import com.musinsa.item.common.util.MessageUtil

enum class Category(val value: String) {
    TOPS("category.tops"),
    OUTERWEAR("category.outerwear"),
    PANTS("category.pants"),
    SNEAKERS("category.sneakers"),
    BAGS("category.bags"),
    HATS("category.hats"),
    SOCKS("category.socks"),
    ACCESSORIES("category.accessories");

    companion object {
        @JvmStatic
        fun fromValue(value: String): Category =
            entries.find { MessageUtil.getMessage(it.value) == value } ?: throw IllegalArgumentException(MessageUtil.getMessage("category.error.invalid"))
    }
}
