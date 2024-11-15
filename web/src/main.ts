import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import {useCategoryStore} from "@/stores/categoryStore";

import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura';
import 'primeicons/primeicons.css';


const app = createApp(App)

app.use(createPinia())
app.use(PrimeVue, {
  theme: {
    preset: Aura,
    options: {
      prefix: 'p',
      darkModeSelector: false,
      cssLayer: false
    }
  }
});
app.use(router)

async function initializeApp() {
  const categoryStore = useCategoryStore();
  await categoryStore.fetchCategories(); // 카테고리 데이터를 먼저 불러오기

  app.mount('#app');
}

initializeApp();
