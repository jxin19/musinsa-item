import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/category-summary',
      name: 'category-summary',
      component: () => import('../views/CategorySummary.vue'),
    },
    {
      path: '/retail-price-by-brand-and-category',
      name: 'retail-price-by-brand-and-category',
      component: () => import('../views/RetailPriceByBrandAndCategory.vue'),
    },
    {
      path: '/lowest-highest-price-by-category',
      name: 'lowest-highest-price-by-category',
      component: () => import('../views/LowestHighestPriceByCategory.vue'),
    },
    {
      path: '/brand',
      name: 'brand',
      component: () => import('../views/Brand.vue'),
    },
    {
      path: '/item',
      name: 'item',
      component: () => import('../views/Item.vue'),
    },
  ],
})

export default router
