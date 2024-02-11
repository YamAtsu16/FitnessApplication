import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import LoginForm from '@/views/LoginForm.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: LoginForm
  },
  {
    path: '/top',
    name: 'top',
    component: () => import('../views/BaseLayout.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
