import { defineStore } from "pinia";
import { getRoutes } from "@/apis/permission";
import type { Permission } from "@/types/permission";
import type { RouteRecordRaw } from "vue-router";

const loadView = (str: string) => {
  if (str === "Layout") {
    return "@/layout/index.vue";
  }
  return `@/views/${str}.vue`;
};

export const usePermissionStore = defineStore("permission", {
  state: () => ({
    // 路由
    routes: [],
  }),
  actions: {
    setRoutes(routes: []) {
      this.routes = routes;
    },
    async generateRoutes() {
      const res = await getRoutes();
      console.log("权限列表", res);
      const routes = [];
      const permissionTree = res.data;
      permissionTree.forEach((permission: Permission) => {
        const route = permission2Route(permission);
        routes.push(route);
      });
      //
      function permission2Route(permission: Permission) {
        const route: RouteRecordRaw = {
          name: permission.permissionName,
          path: permission.path,
          component: () => import("@/views" + permission.component + ".vue"),
          children: [],
        };
        if (permission.children && permission.children.length > 0) {
          const children = permission.children;
          children.forEach((child) => {
            // 深度
            const childRoute = permission2Route(child);
            route.children.push(childRoute);
          });
        }
        return route;
      }
    },
  },
});
