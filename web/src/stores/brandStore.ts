import {defineStore} from 'pinia';
import axios, {AxiosError} from 'axios';
import type {Brand} from "@/interface/Brand";

export const useBrandStore = defineStore('brand', {
  state: () => ({
    list: [] as Brand[],
    rawdata: '',
  }),
  actions: {
    async fetchList() {
      try {
        const response = await axios.get('/api/brand/list');
        this.list = response.data.list as Brand[];
        this.rawdata = response.data;
      } catch (error) {
        if (error instanceof AxiosError && error.response) {
          this.rawdata = error.response.data;
        } else {
          console.log(error);
        }
      }
    },
    async createBrand(name: string) {
      try {
        const response = await axios.post('/api/brand', {
          name: name
        });

        this.list.push(response.data);

        this.rawdata = response.data;
      } catch (error) {
        if (error instanceof AxiosError && error.response) {
          this.rawdata = error.response.data;
        } else {
          console.log(error);
        }
      }
    },
    async updateBrand(brand: Brand) {
      try {
        const response = await axios.put(`/api/brand/${brand.id}`, {
          name: brand.name
        });

        // 기존 항목 업데이트
        const index = this.list.findIndex((obj) => obj.id === brand.id);
        if (index !== -1) {
          this.list[index] = { ...this.list[index], ...response.data };
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
    async deleteBrand(id: number) {
      try {
        const response = await axios.delete(`/api/brand/${id}`);
        this.list = this.list.filter((item) => item.id !== id);
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
})
