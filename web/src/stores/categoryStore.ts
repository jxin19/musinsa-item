import {defineStore} from 'pinia';
import axios from 'axios';
import type {Category} from "@/interface/Category";

export const useCategoryStore = defineStore('category', {
  state: () => ({
    categories: [] as Category[],
    selectedCategory: '', // 선택된 카테고리
  }),
  actions: {
    async fetchCategories() {
      try {
        const response = await axios.get<{ list: Category[] }>('/api/category/list');
        this.categories = response.data.list;
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    },
    selectCategory(category: string) {
      this.selectedCategory = category;
    },
    resetCategory() {
      this.selectedCategory = this.categories[0]['name'];
    },
    findCategoryValue(name: string): string | undefined {
      const category = this.categories.find((cat) => cat['name'] === name);
      return category ? category['value'] : undefined;
    }
  },
});
