<template>
  <div>角色管理</div>
  <!-- 筛选栏 -->
  <el-form ref="queryFormRef" :inline="true" :model="queryParams">
    <!-- 用户名 -->
    <el-form-item label="角色名称" prop="username">
      <el-input v-model="queryParams.roleName"></el-input>
    </el-form-item>
    <!-- 角色 -->
    <el-form-item label="角色编码" prop="roleCode">
      <el-input v-model="queryParams.roleCode"></el-input>
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
    <el-button type="primary" icon="Plus" @click="insertRole">添加</el-button>
    <!-- 删除 -->
    <el-button type="danger" icon="Delete" @click="delSelectedRoles"
      >删除</el-button
    >
  </div>
  <!-- 表格 -->
  <div>
    <el-table
      ref="tableRef"
      :data="tableData"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="roleName" label="角色名称"></el-table-column>
      <el-table-column prop="roleCode" label="角色编码"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
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
          <el-button size="small" type="warning" @click="assignItem(row)"
            >授权</el-button
          >
          <el-button size="small" type="danger" @click="delItem(row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      layout="total, prev, pager, next"
      :total="total"
      @change="onPageChange"
    ></el-pagination>
  </div>
  <!-- 新增用户弹窗 -->
  <el-dialog v-model="formDialogVisible" title="新增角色" width="500">
    <role-form :roleId="editRoleId" @callback="userFormCallback"></role-form>
  </el-dialog>
  <!-- 授权弹窗 -->
  <el-dialog v-model="assignDialogVisible" title="角色授权">
    <el-tree
      ref="permissionTreeRef"
      style="max-width: 600px"
      node-key="id"
      :props="permissionTreeProps"
      :data="permissionTreeData"
      show-checkbox
    />
    <template #footer>
      <el-button @click="cancelAssign">取消</el-button>
      <el-button type="primary" @click="handleAssign">确定</el-button>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
import { onBeforeMount, reactive, ref } from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import {
  getRolePage,
  deleteRoles,
  assignPermissions,
  getRolePermissions,
} from "@/apis/role.ts";
import { getPermissionTree } from "@/apis/permission.ts";
import type { Page } from "@/types/page";
import type { Role, RoleFilter } from "@/types/role";
import RoleForm from "./RoleForm.vue";

const queryFormRef = ref();
const permissionTreeRef = ref();
const editRoleId = ref("");
const multipleSelection = ref<Role[]>([]);

// 查询参数
const queryParams = reactive<RoleFilter>({
  roleName: "",
  roleCode: "",
});
// 分页参数
const page: Page = reactive({
  pageNum: 1,
  pageSize: 10,
});
//
const total = ref(0);
//
const tableData = ref<Role[]>([]);
//
onBeforeMount(() => {
  getRoles();
  initPermissionTree();
});
//
function initPermissionTree() {
  getPermissionTree().then((res) => {
    console.log("权限树", res);
    permissionTreeData.value = res.data;
  });
}
//
function getRoles() {
  getRolePage(page, queryParams).then((res) => {
    console.log("角色列表", res);
    tableData.value = res.data.records;
    total.value = Number(res.data.total);
  });
}
//
function insertRole() {
  console.log("添加");
  editRoleId.value = "";
  formDialogVisible.value = true;
}
// 批量删除
async function delSelectedRoles() {
  console.log("删除选中");
  try {
    if (multipleSelection.value.length === 0) {
      ElMessage.warning("请选择要删除的角色");
      return;
    }
    await showWaning();
    const ids = multipleSelection.value.map((item) => item.id) as string[];
    if (ids.length > 0) {
      await deleteRoles(ids);
    }
  } catch (error) {}
}
// 单个删除
async function delItem(row: Role) {
  console.log("删除", row);
  try {
    await showWaning();
    if (row.id) {
      await deleteRoles([row.id]);
      getRoles();
    }
  } catch (error) {}
}
async function showWaning() {
  await ElMessageBox.confirm("确定要删除？", "Warning", {
    confirmButtonText: "OK",
    cancelButtonText: "Cancel",
    type: "warning",
  });
}
// 编辑角色
function editItem(row: Role) {
  console.log("编辑", row);
  editRoleId.value = row.id ?? "";
  formDialogVisible.value = true;
}
// 授权，显示授权弹窗
async function assignItem(row: Role) {
  if (permissionTreeRef.value) {
    permissionTreeRef.value.setCheckedKeys([]);
  }
  assignDialogVisible.value = true;
  editRoleId.value = row.id ?? "";
  const res = await getRolePermissions(editRoleId.value);
  permissionTreeRef.value.setCheckedKeys(res.data);
  console.log("角色权限", res);
}
// 分页改变
function onPageChange(currentPage: number, pageSize: number) {
  console.log("分页改变", currentPage, pageSize);
  getRoles();
}
//
const formDialogVisible = ref(false);
// 授权弹窗显示
const assignDialogVisible = ref(false);
//
function userFormCallback(event: String) {
  formDialogVisible.value = false;
  getRoles();
}
//
function handleSelectionChange(val: Role[]) {
  console.log("选中", val);
  multipleSelection.value = val;
}
function toQuery() {
  console.log("查询");
  getRoles();
}
function resetQuery() {
  console.log("重置");
  queryFormRef.value.resetFields();
  getRoles();
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
  await assignPermissions(editRoleId.value, permissionKeys);
  assignDialogVisible.value = false;
}
</script>
<style lang="scss" scoped></style>
