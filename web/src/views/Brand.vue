<template>
  <PageTitle title="브랜드 추가/업데이트/삭제"/>

  <div class="container">
    <div class="flex flex-col gap-1">
      <div class="input-button-group">
        <InputText v-model="newBrandName" name="name" type="text" placeholder="새 브랜드명" />
        <Button label="추가" class="custom-button" @click="onAdd(newBrandName)" />
      </div>

      <DataTable v-model:editingRows="editingRows"
                 :value="brandStore.list"
                 editMode="row"
                 dataKey="id"
                 @row-edit-init="onRowEditInit"
                 @row-edit-save="onRowEditSave"
                 @row-edit-cancel="onRowEditCancel"
                 :pt="{
                      table: { style: 'min-width: 50rem' },
                      column: {
                          bodycell: ({ state }: any) => ({
                              style: state['d_editing'] && 'padding-top: 0.75rem; padding-bottom: 0.75rem'
                          })
                      }
                  }"
      >
        <Column field="id" header="번호" />
        <Column field="name" header="브랜드">
          <template #editor="{ data, field }">
            <InputText v-model="data[field]" fluid />
          </template>
        </Column>
        <Column :rowEditor="true" style="width: 10%; min-width: 7rem" bodyStyle="text-align:center" />
        <Column style="width: 10%; min-width: 7rem" bodyStyle="text-align:center">
          <template #body="slotProps">
            <Button icon="pi pi-trash" severity="contrast" variant="text" rounded @click="onDelete(slotProps.data)" />
          </template>
        </Column>
      </DataTable>
    </div>

    <Codebox :code="brandStore.rawdata" />
  </div>
</template>

<script setup lang="ts">
import PageTitle from "@/components/PageTitle.vue";
import Codebox from "@/components/Codebox.vue";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import InputText from "primevue/inputtext";
import Button from 'primevue/button';
import { useBrandStore } from "@/stores/brandStore";
import { ref } from 'vue';
import type {Brand} from "@/interface/Brand";

const brandStore = useBrandStore();
const editingRows = ref<Brand[]>([]);
const newBrandName = ref('');

brandStore.fetchList();

function onRowEditInit(event: { data: any }) {
  editingRows.value.push(event.data);
}

function onRowEditSave(event: { data: Brand, newData: Brand }) {
  brandStore.updateBrand(event.newData);
  editingRows.value.splice(editingRows.value.indexOf(event.newData), 1);
}

function onRowEditCancel(event: { data: any, index: number }) {
  editingRows.value.splice(editingRows.value.indexOf(event.data), 1);
}

function onAdd(name: string) {
  brandStore.createBrand(name);
  newBrandName.value = '';
}

function onDelete(brand: Brand) {
  brandStore.deleteBrand(brand.id);
}
</script>

<style scoped>
.p-button {
  color: #6c757d;
  background: none;
  border: 0;
}

.input-button-group {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 1rem;

  .custom-button {
    background-color: black;
    border: none;
    color: white;

    &:hover {
      background-color: #333;
    }
  }
}
</style>
