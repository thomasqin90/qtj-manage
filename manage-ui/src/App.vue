<script setup lang="ts">
import { RouterView } from "vue-router";
</script>

<template>
  <RouterView />
</template>

<style lang="scss">
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
}
#app {
  height: 100%;
}
/* 全局强制禁用默认动画 */
::view-transition {
  animation: none !important;
}
::view-transition-old(root),
::view-transition-new(root) {
  animation: none !important;
  mix-blend-mode: normal !important;
  display: block !important;
  overflow: hidden !important;
}
:root {
  --old-z: 1;
  --new-z: 999;
}
/* 动态层级 */
::view-transition-old(root) {
  z-index: var(--old-z) !important;
}
::view-transition-new(root) {
  z-index: var(--new-z) !important;
}
</style>
