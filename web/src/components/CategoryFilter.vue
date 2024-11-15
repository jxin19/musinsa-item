<template>
  <ul class="filter-container">
    <li v-for="category in categoryStore.categories"
        :key="category.name"
        :class="['filter-button', categoryStore.selectedCategory === category.name ? 'active' : '']"
        @click="categoryStore.selectCategory(category.name)"
    >
      {{ category.value }}
    </li>
  </ul>
</template>

<script setup lang="ts">
import {useCategoryStore} from "@/stores/categoryStore";

const categoryStore = useCategoryStore();
categoryStore.resetCategory();
</script>

<style scoped>
.filter-container {
  display: flex;
  gap: 10px;
  padding: 7px 20px;

  .filter-button {
    padding: 5px 15px;
    margin: 0;
    border-radius: 20px;
    border: 1px solid #bbb;
    background-color: #fff;
    color: #000;
    text-align: center;
    cursor: pointer;
    transition: all 0.3s ease;
    font-size: 12px;
    list-style: none;

    &.active {
      background-color: #111;
      color: #fff;
      border: none;
    }

    &:hover {
      border-color: #333;
    }
  }
}
</style>
