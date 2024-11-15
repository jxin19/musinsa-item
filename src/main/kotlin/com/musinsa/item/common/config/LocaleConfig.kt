package com.musinsa.item.common.config

import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.Locale

@Configuration
class LocaleConfig : WebMvcConfigurer {

    @Bean
    fun localeResolver(): AcceptHeaderLocaleResolver {
        return object : AcceptHeaderLocaleResolver() {
            init {
                val supportedLocales = listOf(
                    Locale.KOREA,
                    Locale.US,
                    Locale.SIMPLIFIED_CHINESE
                )
                setSupportedLocales(supportedLocales)
                setDefaultLocale(Locale.KOREA)
            }

            override fun resolveLocale(request: HttpServletRequest): Locale {
                val headerLang = request.getHeader("Accept-Language")
                return if (headerLang.isNullOrBlank()) {
                    defaultLocale ?: Locale.KOREA
                } else {
                    Locale.lookup(
                        Locale.LanguageRange.parse(headerLang),
                        supportedLocales
                    ) ?: defaultLocale ?: Locale.KOREA
                }
            }
        }
    }

    @Bean
    fun messageSource(): MessageSource {
        return ResourceBundleMessageSource().apply {
            setBasenames("messages")
            setDefaultEncoding("UTF-8")
            setDefaultLocale(Locale.KOREA)
            setUseCodeAsDefaultMessage(true)
            setCacheSeconds(10)
        }
    }

}
