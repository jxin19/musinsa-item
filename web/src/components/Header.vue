<template>
  <header>
    <!-- 로고 -->
    <a href="/">
      <img src="/logo.png" alt="logo" width="152"/>
    </a>
  </header>

  <!-- 네비게이션 바 -->
  <div class="navbar">
    <RouterLink :to="{name: 'category-summary'}" class="sale">카테고리별 최저가 조회</RouterLink>
    <RouterLink :to="{name: 'retail-price-by-brand-and-category'}">브랜드별 카테고리 조회</RouterLink>
    <RouterLink :to="{name: 'lowest-highest-price-by-category'}">카테고리별 최저/최고가 조회</RouterLink>
    <RouterLink :to="{name: 'brand'}">브랜드 관리</RouterLink>
    <RouterLink :to="{name : 'item'}">상품 관리</RouterLink>
  </div>

  <!-- 무료 배송 배너 -->
  <div class="banner">
    [MUSINSA] Java(Kotlin) Backend Engineer - 신민준
  </div>
</template>

<style scoped>
header {
  position: relative;
  margin: 0 auto;
}

.navbar {
  display: flex;
  justify-content: flex-start;
  background-color: #fff;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;

  a {
    margin: 0.25rem 1.25rem;
    text-decoration: none;
    font-weight: bold;
    font-size: 17px;
    color: #000;
    &:hover {
      text-decoration: underline;
    }
    &.sale {
      color: #ff0000;
    }
  }
}

.banner {
  background-color: #111;
  color: #fff;
  text-align: center;
  padding: 10px 0;
  font-size: 14px;
  font-weight: bold;
}
</style>
<script setup lang="ts">
</script>
