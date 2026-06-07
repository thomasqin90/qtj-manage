<template>
  <!-- 有子菜单：渲染 el-sub-menu -->
  <el-sub-menu v-if="item.children && item.children.length" :index="item.index">
    <template #title>
      <el-icon v-if="item.icon"><component :is="item.icon" /></el-icon>
      <span>{{ item.name }}</span>
    </template>
    <!-- 递归：循环子级，调用自身 -->
    <the-menu-item
      v-for="child in item.children"
      :key="child.id"
      :item="child"
    />
  </el-sub-menu>
  <!-- 无子菜单：渲染 el-menu-item -->
  <el-menu-item v-else :index="item.index">
    <el-icon v-if="item.icon"><component :is="item.icon" /></el-icon>
    <span>{{ item.name }}</span>
  </el-menu-item>
</template>

<script lang="ts" setup>
interface Menu {
  id: number
  name: string
  index: string
  icon?: string
  children?: Menu[]
}
interface Props {
  item: Menu
}

// 接收菜单数据
const props = defineProps<Props>()
const item = props.item;

</script>