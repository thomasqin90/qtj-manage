import { defineStore } from "pinia";
import { getRoutes } from "@/apis/user";
import type { Permission } from "@/types/permission";
import type { RouteRecordRaw } from "vue-router";
import { addAsyncRoutes } from "@/router";
import componentMap from "@/router/componentMap";

export const usePermissionStore = defineStore("permission", {
  state: () => ({
    // 路由
    menuRoutes: [] as RouteRecordRaw[],
    permissions: [] as string[]
  }),
  actions: {
    setRoutes(routes: RouteRecordRaw[]) {
      this.menuRoutes = routes;
    },
    // 权限转路由
    async generateRoutes() {
      console.log("生成路由", componentMap);
      // 获取权限列表
      const res = await getRoutes();
      console.log("权限列表", res);
      const routes: RouteRecordRaw[] = [];
      const permissionTree = res.data;
      permissionTree.forEach((permission: Permission) => {
        const route = permission2Route(permission);
        routes.push(route);
      });
      this.setRoutes(routes);
      addAsyncRoutes(routes);
      //
      function permission2Route(permission: Permission): RouteRecordRaw {
        const route: RouteRecordRaw = {
          name: permission.permissionName,
          path: permission.path,
          component: componentMap[permission.component],
          children: [],
          meta: {
            title: permission.permissionName,
            icon: permission.icon,
          },
        };
        if (permission.children && permission.children.length > 0) {
          const children = permission.children;
          children.forEach((child) => {
            // 深度遍历
            const childRoute = permission2Route(child);
            route.children.push(childRoute);
          });
        }
        return route;
      }
    },
    // 获取用户的权限
    async getPermissions() {

    }
  },
  getters: {
    hasRoutes(): boolean {
      return this.menuRoutes.length > 0;
    },
  },
});
