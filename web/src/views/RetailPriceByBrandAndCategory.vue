<template>
  <PageTitle title="단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회"/>

  <div class="container">
    <DataTable :value="itemStore.list" tableStyle="max-width: 100%">
      <Column field="카테고리" header="카테고리" />
      <Column field="가격" header="가격" style="text-align:right;" />
      <ColumnGroup type="footer">
        <Row>
          <Column footer="브랜드" footerStyle="font-weight:bold;" />
          <Column :footer="itemStore.brand" footerStyle="text-align:right; font-weight:bold;" />
        </Row>
        <Row>
          <Column footer="총액" footerStyle="font-weight:bold;" />
          <Column :footer="itemStore.total.toString()" footerStyle="text-align:right; font-weight:bold;" />
        </Row>
      </ColumnGroup>
    </DataTable>

    <Codebox :code="itemStore.rawdata" />
  </div>
</template>

<script setup lang="ts">
import PageTitle from "@/components/PageTitle.vue";
import Codebox from "@/components/Codebox.vue";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import ColumnGroup from "primevue/columngroup";
import Row from "primevue/row";
import { useItemStore } from "@/stores/itemStore";

const itemStore = useItemStore();
itemStore.fetchRetailPriceAndBrandAndCategory();
</script>
