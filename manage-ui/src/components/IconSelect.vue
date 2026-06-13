<template>
  <el-input
    v-model="iconVal"
    placeholder="选择图标"
    @click="dialogShow = true"
    clearable
  >
    <template #prepend>
      <component :is="iconVal" style="height:32px; width:32px;" />
    </template>
  </el-input>

  <el-dialog v-model="dialogShow" title="选择图标" width="65%">
    <el-input
      v-model="searchKey"
      placeholder="搜索图标"
      style="margin-bottom: 12px"
    />
    <div class="icon-wrap">
      <div
        v-for="name in filterIcons"
        :key="name"
        class="icon-cell"
        :class="{ active: iconVal === name }"
        @click="chooseIcon(name)"
      >
        <!-- 图标 -->
        <component :is="name" />
        <div class="icon-name">{{ name }}</div>
      </div>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, computed, getCurrentInstance } from "vue";
// 双向绑定
const props = defineProps<{ modelValue?: string }>();
const emit = defineEmits(["update:modelValue"]);
const { appContext } = getCurrentInstance()!;
// 全部图标
const allIcons = appContext.config.globalProperties.$iconList as string[];

const dialogShow = ref(false);
const searchKey = ref("");

const iconVal = computed({
  get() {
    return props.modelValue ?? "";
  },
  set(v) {
    emit("update:modelValue", v);
  },
});
// 筛选后的图标集合
const filterIcons = computed(() => {
  if (!searchKey.value) return allIcons;
  return allIcons.filter((n) =>
    n.toLowerCase().includes(searchKey.value.toLowerCase()),
  );
});
// 选中图标
const chooseIcon = (name: string) => {
  iconVal.value = name;
  dialogShow.value = false;
};
</script>

<style scoped>
.icon-wrap {
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  gap: 8px;
  max-height: 420px;
  overflow-y: auto;
}
.icon-cell {
  text-align: center;
  padding: 10px 4px;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  cursor: pointer;
}
.icon-cell.active {
  border-color: #409eff;
  background: #ecf5ff;
}
.icon-name {
  font-size: 11px;
  color: #909399;
  margin-top: 4px;
  word-break: break-all;
}
</style>
