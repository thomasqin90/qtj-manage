<template>
  <div>权限管理</div>
  <!-- 筛选栏 -->
  <el-form ref="queryFormRef" :inline="true" :model="queryParams">
    <el-form-item label="权限名称" prop="permissionName">
      <el-input v-model="queryParams.permissionName"></el-input>
    </el-form-item>
    <!-- 按钮 -->
    <el-form-item>
      <el-button type="primary" @click="toQuery">查询</el-button>
      <el-button @click="resetQuery">重置</el-button>
    </el-form-item>
  </el-form>
  <!-- 操作按钮 -->
  <div>
    <!-- 添加 -->
    <el-button type="primary" icon="Plus" @click="insertPermission"
      >添加</el-button
    >
  </div>
  <!-- 表格 -->
  <div>
    <!-- 树形表格 -->
    <el-table ref="tableRef" :data="tableData" row-key="id">
      <el-table-column prop="permissionName" label="权限名称"></el-table-column>
      <el-table-column prop="permissionCode" label="权限编码"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column prop="permissionType" label="类型"></el-table-column>
      <el-table-column prop="path" label="路径"></el-table-column>
      <el-table-column prop="component" label="组件"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag v-if="row.status === 1" type="success">启用中</el-tag>
          <el-tag v-else type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="editItem(row)"
            >编辑</el-button
          >
          <el-button size="small" type="primary" @click="insertChild(row)"
            >新增</el-button
          >
          <el-button size="small" type="danger" @click="delItem(row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
  <!-- 新增权限弹窗 -->
  <el-dialog v-model="formDialogVisible" title="新增角色" width="500">
    <permission-form :parentId="parentId" :permission-tree="tableData" @callback="userFormCallback"></permission-form>
  </el-dialog>
</template>
<script lang="ts" setup>
import { onBeforeMount, reactive, ref } from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import { getPermissionTree } from "@/apis/permission.ts";
import type { Permission, PermissionFilter } from "@/types/permission";
import PermissionForm from "./PermissionForm.vue";

const queryFormRef = ref();
const permissionTreeRef = ref();
const editPermissionId = ref("");
const multipleSelection = ref<Permission[]>([]);
const permissionTypeList = ref([]);
const parentId = ref("");
// 查询参数
const queryParams = reactive<PermissionFilter>({
  permissionName: "",
});
//
const tableData = ref<Permission[]>([]);
//
onBeforeMount(() => {
  //
  refreshPermissionTree();
});
// 分页插叙权限列表
async function refreshPermissionTree() {
  const res = await getPermissionTree(queryParams);
  tableData.value = res.data;
}
// 新增权限，显示新增权限弹窗
function insertPermission() {
  console.log("添加");
  editPermissionId.value = "";
  formDialogVisible.value = true;
}
// 批量删除
async function delSelectedPermissions() {
  console.log("删除选中");
  try {
    if (multipleSelection.value.length === 0) {
      ElMessage.warning("请选择要删除的角色");
      return;
    }
    await showWaning();
    const ids = multipleSelection.value.map((item) => item.id) as string[];
    if (ids.length > 0) {
      await deletePermissions(ids);
    }
  } catch (error) {}
}
// 单个删除
async function delItem(row: Permission) {
  console.log("删除", row);
  try {
    await showWaning();
    if (row.id) {
      await deletePermissions([row.id]);
      refreshPermissionTree();
    }
  } catch (error) {}
}
// 编辑角色
function editItem(row: Permission) {
  console.log("编辑", row);
  editPermissionId.value = row.id ?? "";
  formDialogVisible.value = true;
}
// 新增子权限
function insertChild(row: Permission) {
  console.log("新增子权限", row);
  editPermissionId.value = row.id ?? "";
  formDialogVisible.value = true;
}
// 删除警告
async function showWaning() {
  await ElMessageBox.confirm("确定要删除？", "Warning", {
    confirmButtonText: "OK",
    cancelButtonText: "Cancel",
    type: "warning",
  });
}
// 分页改变
function onPageChange(currentPage: number, pageSize: number) {
  console.log("分页改变", currentPage, pageSize);
  refreshPermissionPage();
}
//
const formDialogVisible = ref(false);
// 授权弹窗显示
const assignDialogVisible = ref(false);
//
function userFormCallback(event: String) {
  formDialogVisible.value = false;
  refreshPermissionTree();
}
//
function handleSelectionChange(val: Permission[]) {
  console.log("选中", val);
  multipleSelection.value = val;
}
function toQuery() {
  console.log("查询");
  refreshPermissionTree();
}
function resetQuery() {
  console.log("重置");
  queryFormRef.value.resetFields();
  refreshPermissionTree();
}
// 权限树
const permissionTreeProps = {
  children: "children",
  label: "permissionName",
};
//
const permissionTreeData = ref([]);
//
function cancelAssign() {
  assignDialogVisible.value = false;
}
//
async function handleAssign() {
  console.log("授权确定");
  const permissionKeys = permissionTreeRef.value.getCheckedKeys() as string[];
  console.log("权限keys", permissionKeys);
  await assignPermissions(editPermissionId.value, permissionKeys);
  assignDialogVisible.value = false;
}
</script>
<style lang="scss" scoped></style>
