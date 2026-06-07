import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../layouts/MainLayout.vue'
import HomeView from '../views/HomeView.vue'
import The404View from '../views/The404View.vue'
import LoginView from '../views/LoginView.vue'
import { useTagsViewStore } from '@/stores/tabsView.ts'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: MainLayout,
      redirect: '/home',
      children: [
        {
          path: 'home',
          name: 'home',
          component: HomeView,
        },
        {
          path: 'user',
          name: 'user',
          component: () => import("@/views/UserManageView.vue")
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/:pathMatch(.*)*',
      name: '404',
      component: The404View,
    }
  ],
})
//
router.afterEach((to) => {
  const tagsViewStore = useTagsViewStore()
  tagsViewStore.addView(to)
})
//
export default router
