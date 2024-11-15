<template>
  <PageTitle title="카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회"/>
  <CategoryFilter/>

  <div class="container">
    <DataTable :value="itemStore.list">
      <Column field="구분" header="구분"/>
      <Column field="브랜드" header="브랜드"/>
      <Column field="가격" header="가격" style="text-align:right;"/>
    </DataTable>

    <Codebox :code="itemStore.rawdata"/>
  </div>
</template>

<script setup lang="ts">
import PageTitle from "@/components/PageTitle.vue";
import Codebox from "@/components/Codebox.vue";
import CategoryFilter from "@/components/CategoryFilter.vue";
import DataTable from "primevue/datatable";
import Column from "primevue/column";

import {useItemStore} from "@/stores/itemStore";
import {useCategoryStore} from "@/stores/categoryStore";
import {onMounted, watch} from "vue";

const itemStore = useItemStore()
const categoryStore = useCategoryStore()

function fetchDataForCategory() {
  const categoryValue = categoryStore.findCategoryValue(categoryStore.selectedCategory);
  if (categoryValue) {
    itemStore.fetchLowestHighestPriceByCategory(categoryValue);
  }
}

onMounted(fetchDataForCategory);

watch(() => categoryStore.selectedCategory, fetchDataForCategory);
</script>
