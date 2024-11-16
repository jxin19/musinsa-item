# [신민준] 무신사 코디 서비스 API(과제)

## 1. 프로젝트 목적
이 프로젝트는 무신사의 새로운 코디 완성 서비스를 위한 백엔드 API를 구현합니다.
고객들이 8개의 카테고리(상의, 아우터, 바지, 스니커즈, 가방, 모자, 양말, 액세서리)에서 상품을 하나씩 선택하여 코디를 완성할 수 있도록 지원합니다.

주요 기능:
1. 카테고리별 최저가 브랜드와 가격, 총액 조회
2. 단일 브랜드로 전체 카테고리 구매 시 최저가 브랜드와 총액 조회
3. 특정 카테고리의 최저가 및 최고가 브랜드와 가격 조회
4. 브랜드 및 상품 관리 (추가, 수정, 삭제)


## 2. 기술 스펙

### API
- 언어: Kotlin 1.9.25
- 프레임워크: Spring Boot 3.3.5
- 빌드 도구: Gradle 8.10.2
- 데이터베이스: H2
- ORM: Spring Data JPA, QueryDSL
- API 문서화: Swagger (SpringDoc OpenAPI)
- 테스트: JUnit 5, Mockito
- 컨테이너: Docker(Docker Compose)

### Frontend
- 언어: node21 (설치: https://nodejs.org/en/download/prebuilt-installer)
- 프레임워크: vue3
- 빌드 도구: vite, npm
  - node 설치 시 npm도 함께 설치됩니다.

## 주요 디펜던시
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- QueryDSL
- H2 Database
- Flyway
- SpringDoc OpenAPI UI
- Mockk
- Testcontainers


## 프로젝트 구조
```
src
├── main
│   ├── kotlin
│   │   └── com.musinsa.item
│   │       ├── brand
│   │       │   ├── application
│   │       │   │   ├── dto
│   │       │   │   └── impl
│   │       │   ├── domain
│   │       │   │   └── property
│   │       │   ├── repository
│   │       │   └── ui
│   │       │       └── dto
│   │       ├── category
│   │       │   ├── application
│   │       │   │   ├── dto
│   │       │   │   └── impl
│   │       │   ├── domain
│   │       │   │   └── property
│   │       │   └── ui
│   │       │       └── dto
│   │       ├── common
│   │       │   ├── config
│   │       │   ├── domain
│   │       │   ├── dto
│   │       │   ├── exception
│   │       │   ├── handler
│   │       │   └── util
│   │       └── item
│   │           ├── application
│   │           │   ├── dto
│   │           │   └── impl
│   │           ├── domain
│   │           │   └── property
│   │           ├── repository
│   │           └── ui
│   │               ├── dto
│   │               └── extension
│   └── resources
│       ├── db/migration
│       ├── messages
│       └── application.yml
├── test
│   └── kotlin
│       └── com.musinsa.item
│           ├── brand
│           ├── category
│           ├── item
│           └── util
├── build.gradle.kts
└── docker-compose.yml
```

## 주요 코드 설명

### Brand, Item 엔티티
 - 도메인 주도 설계 원칙 적용
 - id 값을 제외한 프로퍼티는 원시타입이 아닌 Wrapper Class로 생성하여 재사용성을 높이도록 했습니다.
 - Wrapper Class에서 유효성검사 기능을 활용하여 불변성을 보장하고, 유효성검사를 활용하여 Type-safety 합니다.
 - 공용 검증 로직 인터페이스(`StringValidator`, `PriceValidator`)를 활용하고 있습니다.

### Command, Query 분리
 - 상태 변경 로직과 조회 로직을 분리하였습니다.
 - Command, Query는 각기 다른 DB를 사용할 수 있습니다.
   - e.g. Command: MySQL, PostgreSQL / Query: Redis, Elasticsearch, MongoDB
   - `다른 방안`: 조회 성능을 향상시키고, 원본 테이블의 부하를 줄이기 위해 Materialized View를 활용하는 방법도 있습니다. 

### JPA
 - N+1 문제를 방지하였습니다.
   - 예시1 (`ItemRepository` 참고)
     - `@EntityGraph` 활용 
      ```
       @EntityGraph(attributePaths = ["brand"])
       override fun findById(id: Long): Optional<Item>
      ```
   - 예시2 (`ItemRepositoryCustomImpl` 참고)
     - `EntityManager`, `JPAQueryFactory` 활용
     ```
     queryFactory.select(
            qItem.brand,
            qItem.retailPrice._value.sum())
        )
        .from(qItem)
        .groupBy(qItem.brand)
        ...
     ```
 - 복잡한 쿼리는 `RepositoryCustom` 클래스를 생성하여 은닉화했습니다.
   - ISP(인터페이스 분리 원칙)

### DB 마이그레이션
 - `Flyway`로 변경 이력을 관리합니다.
 - 충돌없이 DB 변경사항을 공유할 수 있습니다.

### 다국어 설정
 - `MessageUtil`와 `LocaleConfig` 로 `Accept-Language` 헤더 값을 활용하여 자동으로 메시지의 언어를 출력합니다.

### 테스트코드
 - 단위테스트: `xxxTests`, `xxxServiceTests`
 - 인수테스트: `xxxAcceptanceTests`
 - 통합테스트: `xxxIntegrationTests`


## API 엔드포인트

### 카테고리
[http파일 보기](./request/카테고리.http)
- GET /category/list: 카테고리 목록 조회

### 브랜드
[http파일 보기](./request/브랜드.http)
- POST /brand: 브랜드 추가
- PUT /brand/{id}: 브랜드 수정
- DELETE /brand/{id}: 브랜드 삭제

### 상품
[http파일 보기](./request/상품.http)
- GET /item/lowest-retail-prices-and-brands-of-category: 카테고리별 최저가 조회
- GET /item/brand-item-for-lowest-retail-prices: 단일 브랜드 전체 카테고리 최저가 조회
- GET /item/category/{category}/lowest-highest-retail-price-and-brands: 특정 카테고리 최저/최고가 조회
- POST /item: 상품 추가
- PUT /item/{id}: 상품 수정
- DELETE /item/{id}: 상품 삭제


## 실행 방법

1. 프로젝트를 클론합니다:
   ```
   git clone git@github.com:jxin19/musinsa-item.git
   cd [project-directory]
   ```

2. Docker Compose를 사용하여 필요한 서비스를 실행합니다:`docker-compose up -d`
   - H2 Database, API 애플리케이션, Web 애플리케이션을 실행 가능합니다.
   - 실행된 Web 애플리케이션(컨테이너)은 http://localhost:4173 에 접속하여 확인할 수 있습니다. 
   - 실행된 API 애플리케이션(컨테이너)은 http://localhost:8080 입니다.
   - 다음 3,4번 절차는 Docker를 실행하지 않고 애플리케이션을 실행하는 절차입니다.


3. API 애플리케이션을 빌드하고 실행합니다:

    `./gradlew bootRun`
   - 애플리케이 션은 http://localhost:8080 에서 실행됩니다.


4. Web 애플리케이션을 실행합니다:

    ```
    cd web
    npm install
    npm run dev
    ```
   - 애플리케이션은 http://localhost:5173 에서 실행됩니다.
   - `npm run dev`가 실행되지 않는다면, `/usr/local/bin/npm run dev` 명령어로 실행할 수 있습니다.


## 테스트 실행

`./gradlew test`


## API 문서
애플리케이션 실행 후 http://localhost:8080/swagger-ui.html 에서 Swagger UI를 통해 API 문서를 확인할 수 있습니다.
