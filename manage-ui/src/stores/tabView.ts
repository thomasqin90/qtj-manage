import { defineStore } from "pinia";
import { ref } from "vue";
import type { RouteLocationNormalized } from "vue-router";
// 标签
export interface TabView {
  path: string;
  name?: string;
  title: string;
  fullPath: string;
  closable: boolean;
}

export const useTabViewStore = defineStore("tabView", () => {
  // 当前可见标签页列表
  const visitedViews = ref<TabView[]>([]);
  // 添加标签，通过路由
  const addView = (view: RouteLocationNormalized) => {
    if (!view.meta?.title) return;
    // 防止重复添加
    if (visitedViews.value.some((v) => v.path === view.path)) return;

    visitedViews.value.push({
      path: view.path,
      name: view.name as string,
      title: view.meta.title as string,
      fullPath: view.fullPath || view.path,
      closable: view.meta.closable as boolean ?? true,
    });
  };

  // 删除单个标签
  const delView = (view: TabView) => {
    const index = visitedViews.value.findIndex((v) => v.path === view.path);
    if (index > -1) {
      visitedViews.value.splice(index, 1);
    }
  };

  // 删除所有（保留首页）
  const delAllViews = () => {
    visitedViews.value = visitedViews.value.filter((v) => v.path === "/home");
  };

  // 删除其他
  const delOthersViews = (view: TabView) => {
    visitedViews.value = visitedViews.value.filter(
      (v) => v.path === view.path || v.path === "/home",
    );
  };

  return {
    visitedViews,
    addView,
    delView,
    delAllViews,
    delOthersViews,
  };
});
