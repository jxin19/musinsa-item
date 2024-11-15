package com.musinsa.item.common.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("[신민준] 무신사 코디 서비스 API(과제)")
                    .description("무신사 코디 완성 서비스를 위한 백엔드 API")
            )
    }
}
