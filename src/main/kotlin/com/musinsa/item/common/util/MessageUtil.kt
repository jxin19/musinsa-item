package com.musinsa.item.common.util

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.stereotype.Component

@Component
class MessageUtil internal constructor(messageSource: ResourceBundleMessageSource) {
    init {
        Companion.messageSource = messageSource
    }

    companion object {
        private var messageSource: ResourceBundleMessageSource? = null

        fun getMessage(messageCode: String): String {
            val locale = LocaleContextHolder.getLocale()
            return messageSource!!.getMessage(messageCode, null, locale)
        }

        fun getMessage(messageCode: String, vararg args: Any): String {
            val locale = LocaleContextHolder.getLocale()
            return messageSource!!.getMessage(messageCode, args, locale)
        }
    }
}
