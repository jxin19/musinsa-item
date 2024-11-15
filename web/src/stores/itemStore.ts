import { defineStore } from 'pinia';
import axios, {AxiosError} from 'axios';

export const useItemStore = defineStore('item', {
  state: () => ({
    brand: '',
    list: [] as PriceData[],
    total: 0,
    items: [] as Item[],
    rawdata: ''
  }),
  actions: {
    async fetchCategorySummary() {
      try {
        const response = await axios.get('/api/item/lowest-retail-prices-and-brands-of-category');
        this.list = response.data['목록'];
        this.total = response.data['총액'];
        this.rawdata = response.data;
      } catch (error) {
        if (error instanceof AxiosError && error.response) {
          this.rawdata = error.response.data;
        } else {
          console.log(error);
        }
      }
    },
    async fetchRetailPriceAndBrandAndCategory() {
      try {
        const response = await axios.get('/api/item/brand-item-for-lowest-retail-prices');
        this.brand = response.data['최저가']['브랜드'];
        this.list = response.data['최저가']['카테고리'];
        this.total = response.data['최저가']['총액'];
        this.rawdata = response.data;
      } catch (error) {
        if (error instanceof AxiosError && error.response) {
          this.rawdata = error.response.data;
        } else {
          console.log(error);
        }
      }
    },
    async fetchLowestHighestPriceByCategory(category: string) {
      try {
        const response = await axios.get(`/api/item/category/${category}/lowest-highest-retail-price-and-brands`);
        this.list = [
          {구분: "최고가", ...response.data['최고가']},
          {구분: "최저가", ...response.data['최저가']}
        ] as PriceData[];
        this.rawdata = response.data;
      } catch (error) {
        if (error instanceof AxiosError && error.response) {
          this.rawdata = error.response.data;
        } else {
          console.log(error);
        }
      }
    },
    async fetchList() {
      try {
        const response = await axios.get('/api/item/list');
        this.items = response.data.list as Item[];
        this.rawdata = response.data;
      } catch (error) {
        if (error instanceof AxiosError && error.response) {
          this.rawdata = error.response.data;
        } else {
          console.log(error);
        }
      }
    },
    async createItem(item: Item) {
      try {
        const response = await axios.post('/api/item', {
          category: item.category,
          brandId: item.brandId,
          retailPrice: item.retailPrice.replace(/,/g, '')
        });

        this.items.push(response.data);

        this.rawdata = response.data;
      } catch (error) {
        if (error instanceof AxiosError && error.response) {
          this.rawdata = error.response.data;
        } else {
          console.log(error);
        }
      }
    },
    async updateItem(item: Item) {
      try {
        const response = await axios.put(`/api/item/${item.id}`, {
          category: item.category,
          brandId: item.brandId,
          retailPrice: item.retailPrice.replace(/,/g, '')
        });

        // 기존 항목 업데이트
        const index = this.items.findIndex((obj) => obj.id === item.id);
        if (index !== -1) {
          this.items[index] = { ...this.items[index], ...response.data };
        }

        this.rawdata = response.data;
      } catch (error) {
        if (error instanceof AxiosError && error.response) {
          this.rawdata = error.response.data;
        } else {
          console.log(error);
        }
      }
    },
    async deleteItem(id: number) {
      try {
        const response = await axios.delete(`/api/item/${id}`);
        this.items = this.items.filter((item) => item.id !== id);
        this.rawdata = response.data;
      } catch (error) {
        if (error instanceof AxiosError && error.response) {
          this.rawdata = error.response.data;
        } else {
          console.log(error);
        }
      }
    }
  }
});
