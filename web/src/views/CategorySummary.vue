<template>
  <PageTitle title="카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회"/>

  <div class="container">
    <DataTable :value="itemStore.list">
      <Column field="카테고리" header="카테고리" />
      <Column field="브랜드" header="브랜드" />
      <Column field="가격" header="가격"  style="text-align:right;"/>
      <ColumnGroup type="footer">
        <Row>
          <Column footer="총액" footerStyle="font-weight:bold;" />
          <Column :footer="itemStore.total.toString()" :colspan="2" footerStyle="text-align:right; font-weight:bold;" />
        </Row>
      </ColumnGroup>
    </DataTable>

    <Codebox :code="itemStore.rawdata" />
  </div>
</template>

<script setup lang="ts">
import PageTitle from "@/components/PageTitle.vue";
import Codebox from "@/components/Codebox.vue";
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import ColumnGroup from "primevue/columngroup";
import Row from "primevue/row";
import {useItemStore} from "@/stores/itemStore";

const itemStore = useItemStore()
itemStore.fetchCategorySummary()
</script>
