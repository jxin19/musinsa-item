package com.musinsa.item.item.ui.extension

import org.springframework.context.i18n.LocaleContextHolder
import java.math.BigDecimal
import java.text.NumberFormat

fun BigDecimal.formatPrice(): String {
    val formatter = NumberFormat.getNumberInstance(LocaleContextHolder.getLocale())
    return formatter.format(this)
}
