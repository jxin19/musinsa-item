package com.musinsa.item.item

import com.musinsa.item.brand.domain.Brand
import com.musinsa.item.brand.domain.property.BrandName
import com.musinsa.item.brand.repository.BrandRepository
import com.musinsa.item.category.domain.property.Category
import com.musinsa.item.item.domain.Item
import com.musinsa.item.item.domain.property.RetailPrice
import com.musinsa.item.item.repository.ItemRepository
import com.musinsa.item.item.ui.dto.ItemRequest
import com.musinsa.item.util.MessageSourceUtil
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MessageSourceUtil::class)
@ActiveProfiles("test")
@Testcontainers
class ItemIntegrationTests {

    companion object {
        @Container
        private val h2Container = GenericContainer("oscarfonts/h2:latest").apply {
            withExposedPorts(1521)
            withEnv("H2_OPTIONS", "-ifNotExists")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") {
                "jdbc:h2:mem:musinsa;DATABASE_TO_UPPER=TRUE;DB_CLOSE_DELAY=-1"
            }
            registry.add("spring.datasource.driver-class-name") { "org.h2.Driver" }
            registry.add("spring.datasource.username") { "sa" }
            registry.add("spring.datasource.password") { "" }

            registry.add("spring.jpa.show-sql") { "true" }
            registry.add("spring.jpa.hibernate.ddl-auto") { "update" }
            registry.add("spring.jpa.properties.hibernate.format_sql") { "true" }
            registry.add("spring.jpa.properties.hibernate.jdbc.batch_size") { "100" }
            registry.add("spring.jpa.properties.hibernate.jdbc.batch_versioned_data") { "true" }
            registry.add("spring.jpa.properties.hibernate.order_updates") { "true" }

            registry.add("spring.sql.init.mode") { "always" }

            registry.add("spring.flyway.url") { "jdbc:h2:mem:musinsa;DATABASE_TO_UPPER=TRUE" }
            registry.add("spring.flyway.user") { "sa" }
            registry.add("spring.flyway.password") { "" }
            registry.add("spring.flyway.locations") { "classpath:db/migration" }
            registry.add("spring.flyway.baseline-on-migrate") { "true" }
            registry.add("spring.flyway.baseline-version") { "1" }
            registry.add("spring.flyway.enabled") { "true" }
            registry.add("spring.flyway.validate-on-migrate") { "true" }
        }
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @Autowired
    private lateinit var brandRepository: BrandRepository

    private lateinit var nike: Brand
    private lateinit var adidas: Brand

    @BeforeEach
    fun setUp() {
        itemRepository.deleteAll()
        brandRepository.deleteAll()

        nike = brandRepository.save(Brand(name = BrandName("나이키")))
        adidas = brandRepository.save(Brand(name = BrandName("아디다스")))
    }

    @Test
    fun `상품_생성_조회_수정_삭제_통합_테스트`() {
        // 상품 생성
        val createRequest = ItemRequest("상의", nike.id, BigDecimal("10000"))
        val createResult = mockMvc.perform(
            post("/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest))
        )
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.category").value("상의"))
            .andExpect(jsonPath("$.brandName").value("나이키"))
            .andExpect(jsonPath("$.retailPrice").value("10,000"))
            .andReturn()

        val itemId = objectMapper.readTree(createResult.response.contentAsString)["id"].asLong()

        // 상품 조회
        mockMvc.perform(get("/item/list"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list[0].id").value(itemId))
            .andExpect(jsonPath("$.list[0].category").value("상의"))

        // 상품 수정
        val updateRequest = ItemRequest("바지", nike.id, BigDecimal("20000"))
        mockMvc.perform(
            put("/item/$itemId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.category").value("바지"))
            .andExpect(jsonPath("$.retailPrice").value("20,000"))

        // 상품 삭제
        mockMvc.perform(delete("/item/$itemId"))
            .andDo(print())
            .andExpect(status().isOk)

        // 삭제 확인
        mockMvc.perform(get("/item/list"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list").isEmpty)
    }

    @Test
    fun `카테고리별_최저최고가_조회_통합_테스트`() {
        itemRepository.saveAll(
            listOf(
                Item(category = Category.TOPS, retailPrice = RetailPrice(BigDecimal("10000")), brand = nike),
                Item(category = Category.TOPS, retailPrice = RetailPrice(BigDecimal("20000")), brand = adidas)
            )
        )

        mockMvc.perform(get("/item/category/상의/lowest-highest-retail-price-and-brands"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.카테고리").value("상의"))
            .andExpect(jsonPath("$.최저가.브랜드").value("나이키"))
            .andExpect(jsonPath("$.최저가.가격").value("10,000"))
            .andExpect(jsonPath("$.최고가.브랜드").value("아디다스"))
            .andExpect(jsonPath("$.최고가.가격").value("20,000"))
    }

    @Test
    fun `브랜드별_최저가_상품_조회_통합_테스트`() {
        itemRepository.saveAll(
            listOf(
                Item(category = Category.TOPS, retailPrice = RetailPrice(BigDecimal("10000")), brand = nike),
                Item(category = Category.PANTS, retailPrice = RetailPrice(BigDecimal("20000")), brand = nike),
                Item(category = Category.TOPS, retailPrice = RetailPrice(BigDecimal("15000")), brand = adidas),
                Item(category = Category.PANTS, retailPrice = RetailPrice(BigDecimal("25000")), brand = adidas)
            )
        )

        mockMvc.perform(get("/item/lowest-retail-prices-and-brands-of-category"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.목록[0].카테고리").value("상의"))
            .andExpect(jsonPath("$.목록[0].브랜드").value("나이키"))
            .andExpect(jsonPath("$.목록[0].가격").value("10,000"))
            .andExpect(jsonPath("$.목록[1].카테고리").value("바지"))
            .andExpect(jsonPath("$.목록[1].브랜드").value("나이키"))
            .andExpect(jsonPath("$.목록[1].가격").value("20,000"))
    }

    @Test
    fun `단일_브랜드_최저가_조회_통합_테스트`() {
        itemRepository.saveAll(
            listOf(
                Item(category = Category.TOPS, retailPrice = RetailPrice(BigDecimal("10000")), brand = nike),
                Item(category = Category.PANTS, retailPrice = RetailPrice(BigDecimal("20000")), brand = nike),
                Item(category = Category.TOPS, retailPrice = RetailPrice(BigDecimal("15000")), brand = adidas),
                Item(category = Category.PANTS, retailPrice = RetailPrice(BigDecimal("25000")), brand = adidas)
            )
        )

        mockMvc.perform(get("/item/brand-item-for-lowest-retail-prices"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.최저가.브랜드").value("나이키"))
            .andExpect(jsonPath("$.최저가.총액").value("30,000"))
    }
}
