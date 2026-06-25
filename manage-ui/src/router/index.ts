import { createRouter, createWebHistory, type RouteRecordRaw } from "vue-router";
import { useTabViewStore } from "@/stores/tabView.ts";
import { useUserStore } from "@/stores/user.ts";
import { usePermissionStore } from "@/stores/permission.ts";
import { routes, notFoundRoute } from "./staticRoutes.ts";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...routes],
});
//
router.beforeEach(async (to, from, next) => {
  console.log("beforeEach", to, from);
  if (to.meta.ignoreAuth) {
    // 不需要登录校验
    next();
  } else {
    const userStore = useUserStore();
    if (userStore.token) {
      // 判断动态路由表是否为空
      const permissionStore = usePermissionStore();
      if (permissionStore.hasRoutes) {
        // 动态路由表不为空
        next();
      } else {
        // 动态路由表为空,获取动态路由表
        await permissionStore.generateRoutes();
        // 获取动态路由后，重新匹配
        next({
          ...to,
          replace: true,
        });
      }
    } else {
      // 去登录
      next("/login");
    }
  }
});
// 白名单和登录校验；动态路由获取
router.afterEach((to) => {
  // 记录当前路由
  const tagsViewStore = useTabViewStore();
  tagsViewStore.addView(to);
});
export function addAsyncRoutes(routes: RouteRecordRaw[]) {
  routes.forEach((route) => {
    router.addRoute(route);
  });
  router.addRoute(notFoundRoute);
}
//
export default router;
