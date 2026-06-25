import MainLayout from "../layouts/MainLayout.vue";
import HomeView from "../views/HomeView.vue";
import The404View from "../views/The404View.vue";
import LoginView from "../views/LoginView.vue";
import type { RouteRecordRaw } from "vue-router";

//
export const routes: RouteRecordRaw[] = [
  {
    path: "/",
    component: MainLayout,
    redirect: "/home",
    children: [
      {
        path: "home",
        name: "home",
        component: HomeView,
        meta: {
          title: "首页",
          icon: "",
          closable: false,
        },
      },
      {
        path: "personCenter",
        name: "personCenter",
        component: () => import("@/views/personCenter/index.vue"),
        meta: {
          title: "个人中心",
        },
      },
    ],
  },
  // 登录
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      ignoreAuth: true,
    },
  },
  {
    path: "/404",
    name: "404",
    component: The404View,
    meta: {
      ignoreAuth: true,
    },
  },
];
//
export const notFoundRoute: RouteRecordRaw = {
  path: "/:pathMatch(.*)*",
  redirect: "/404",
};
