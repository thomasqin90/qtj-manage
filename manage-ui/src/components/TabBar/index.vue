<template>
  <div>
    <el-tabs v-model="activePath" @tab-change="handleTabChange">
      <el-tab-pane
        v-for="tab in visitedViews"
        :key="tab.path"
        :name="tab.path"
        :label="tab.title"
        closable
        @tab-remove="removeTab"
      />
    </el-tabs>
  </div>
</template>
<script lang="ts" setup>
import { ref } from "vue";
import { type TabView, useTabViewStore } from "@/stores/tabView";
//
const tabViewStore = useTabViewStore();
//
const activePath = ref<string>("");
//
const visitedViews = tabViewStore.visitedViews;
// 标签切换
function handleTabChange(name: string) {
  console.log("handleTabChange", name);

}
// 标签关闭
function removeTab(name: string) {
  console.log("removeTab", name);
  // tabViewStore.delView()
}
</script>
