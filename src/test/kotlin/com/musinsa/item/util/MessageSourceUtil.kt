package com.musinsa.item.util

import com.musinsa.item.common.util.MessageUtil
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.context.support.ResourceBundleMessageSource

class MessageSourceUtil : BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext?) {
        val messageSource = ResourceBundleMessageSource().apply {
            setBasename("messages")
            setDefaultEncoding("UTF-8")
        }
        MessageUtil(messageSource)
    }
}
