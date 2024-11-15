<template>
  <PageTitle title="상품 추가/업데이트/삭제"/>

  <div class="container">
    <div class="flex flex-col gap-1">
      <div class="input-button-group">
        <Select v-model="selectedCategory" :options="categoryStore.categories" optionLabel="value" optionValue="value" placeholder="카테고리" class="w-full md:w-56" />
        <Select v-model="selectedBrandId" :options="brandStore.list" optionLabel="name" optionValue="id" placeholder="브랜드" class="w-full md:w-56" />
        <InputText v-model="newRetailPrice" name="retailPrice" type="number" placeholder="가격"/>
        <Button label="추가" class="custom-button" @click="onAdd(selectedCategory, selectedBrandId, newRetailPrice)"/>
      </div>

      <DataTable v-model:editingRows="editingRows"
                 :value="itemStore.items"
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
        <Column field="id" header="번호"/>
        <Column field="category" header="카테고리">
          <template #editor="{ data, field }">
            <Select v-model="data[field]" :options="categoryStore.categories" optionLabel="value" optionValue="value" placeholder="카테고리" class="w-full md:w-56"/>
          </template>
        </Column>
        <Column field="brandName" header="브랜드">
          <template #editor="{ data, field }">
            <Select v-model="data['brandId']" :options="brandStore.list" optionLabel="name" optionValue="id" placeholder="브랜드" class="w-full md:w-56" />
          </template>
        </Column>
        <Column field="retailPrice" header="가격">
          <template #editor="{ data, field }">
            <InputText
              fluid
              v-model="data[field]"
              :value="formattedPrice(data[field])"
            />
          </template>
        </Column>
        <Column :rowEditor="true" style="width: 10%; min-width: 7rem" bodyStyle="text-align:center"/>
        <Column style="width: 10%; min-width: 7rem" bodyStyle="text-align:center">
          <template #body="slotProps">
            <Button icon="pi pi-trash" severity="contrast" variant="text" rounded @click="onDelete(slotProps.data)"/>
          </template>
        </Column>
      </DataTable>
    </div>

    <Codebox :code="itemStore.rawdata"/>
  </div>
</template>

<script setup lang="ts">
import PageTitle from "@/components/PageTitle.vue";
import Codebox from "@/components/Codebox.vue";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import InputText from "primevue/inputtext";
import Button from 'primevue/button';
import Select from 'primevue/select';
import {useItemStore} from "@/stores/itemStore";
import {useCategoryStore} from "@/stores/categoryStore";
import {useBrandStore} from "@/stores/brandStore";
import {ref} from 'vue';

const itemStore = useItemStore();
const categoryStore = useCategoryStore();
const brandStore = useBrandStore();
const editingRows = ref<Item[]>([]);
const selectedCategory = ref('');
const selectedBrandId = ref<number>(0);
const newRetailPrice = ref('');

itemStore.fetchList();
brandStore.fetchList();

function formattedPrice(value: string | number) {
  const numberValue = typeof value === 'string' ? value.replace(/,/g, '') : value;
  return Number(numberValue);
}

function onRowEditInit(event: { data: any }) {
  editingRows.value.push(event.data);
}

function onRowEditSave(event: { data: Item, newData: Item }) {
  itemStore.updateItem(event.newData);
  editingRows.value.splice(editingRows.value.indexOf(event.newData), 1);
}

function onRowEditCancel(event: { data: any, index: number }) {
  editingRows.value.splice(editingRows.value.indexOf(event.data), 1);
}

function onAdd(category: string, brandId: number, retailPrice: string) {
  const item = {
    category: category,
    brandId: brandId,
    retailPrice: retailPrice
  } as Item;
  itemStore.createItem(item);
  selectedCategory.value = '';
  selectedBrandId.value = 0;
  newRetailPrice.value = '';
}

function onDelete(item: Item) {
  itemStore.deleteItem(item.id);
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
