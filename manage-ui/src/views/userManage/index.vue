<template>
  <div>用户管理</div>
  <!-- 筛选栏 -->
  <el-form ref="queryFormRef" :inline="true" :model="queryParams">
    <!-- 用户名 -->
    <el-form-item label="用户名" prop="username">
      <el-input v-model="queryParams.username"></el-input>
    </el-form-item>
    <!-- 昵称 -->
    <el-form-item label="昵称" prop="nickname">
      <el-input v-model="queryParams.nickname"></el-input>
    </el-form-item>
    <!-- 角色 -->
    <el-form-item label="角色" prop="roleCode">
      <el-select v-model="queryParams.roleId" style="width: 100px">
        <el-option
          v-for="role in roles"
          :label="role.roleName"
          :value="role.id"
        ></el-option>
      </el-select>
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
    <el-button type="primary" icon="Plus" @click="insertUser" v-hasPermission="'system:user:add'">添加</el-button>
    <!-- 删除 -->
    <el-button type="danger" icon="Delete" @click="delSelectedUsers"
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
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="nickname" label="昵称"></el-table-column>
      <el-table-column prop="role" label="角色"></el-table-column>
      <!-- 手机 -->
      <el-table-column prop="phone" label="手机"></el-table-column>
      <!-- 邮箱 -->
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <!-- 状态 -->
      <el-table-column prop="status" label="状态"></el-table-column>
      <el-table-column>
        <template #default="{ row }">
          <el-button type="primary" @click="editItem(row)">编辑</el-button>
          <el-button type="danger" @click="delItem(row)">删除</el-button>
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
  <el-dialog v-model="dialogFormVisible" title="新增用户" width="500">
    <user-form :userId="editUserId" @callback="userFormCallback"></user-form>
  </el-dialog>
</template>
<script lang="ts" setup>
import { onBeforeMount, reactive, ref } from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import UserForm from "./UserForm.vue";
import { getUserList, deleteUsers } from "@/apis/user";
import { getRoleList } from "@/apis/role.ts";
import type { Page } from "@/types/page";
import type { UserFilter } from "@/types/user";
import type { Role } from "@/types/role";

const queryFormRef = ref();
const tableRef = ref();
const editUserId = ref("");
const multipleSelection = ref<User[]>([]);
const roles = ref<Role[]>([]);
// 用户
interface User {
  id: string;
  username: string;
  nickname: string;
  role: string;
  phone: string;
  email: string;
  status: string;
}
// 查询参数
const queryParams = reactive<UserFilter>({
  username: "",
  nickname: "",
  roleId: "",
});
// 分页参数
const page: Page = reactive({
  pageNum: 1,
  pageSize: 10,
});
const total = ref(0);
const tableData = ref<User[]>([]);

onBeforeMount(() => {
  getRoles();
  getUsers();
});

function getRoles() {
  getRoleList().then((res) => {
    console.log("角色列表", res);
    roles.value = res.data;
  });
}

function getUsers() {
  getUserList(page, queryParams).then((res) => {
    console.log("用户列表", res);
    tableData.value = res.data.records;
    total.value = Number(res.data.total);
  });
}

function insertUser() {
  console.log("添加");
  editUserId.value = "";
  dialogFormVisible.value = true;
}

async function delSelectedUsers() {
  console.log("删除选中");
  try {
    if (multipleSelection.value.length === 0) {
      ElMessage.warning("请选择要删除的用户");
      return;
    }
    await showWaning();
    const ids = multipleSelection.value.map((item) => item.id);
    await deleteUsers(ids);
  } catch (error) {}
}

async function delItem(row: User) {
  console.log("删除", row);
  try {
    await showWaning();
    await deleteUsers([row.id]);
    getUsers();
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
function editItem(row: User) {
  console.log("编辑", row);
  editUserId.value = row.id;
  dialogFormVisible.value = true;
}
//
function onPageChange(currentPage: number, pageSize: number) {
  console.log("分页改变", currentPage, pageSize);
  getUsers();
}
//
const dialogFormVisible = ref(false);
function userFormCallback(event: String) {
  dialogFormVisible.value = false;
  getUsers();
}
//
function handleSelectionChange(val: User[]) {
  console.log("选中", val);
  multipleSelection.value = val;
}
function toQuery() {
  console.log("查询");
  getUsers();
}
function resetQuery() {
  console.log("重置");
  queryFormRef.value.resetFields();
  getUsers();
}
</script>
<style lang="scss" scoped></style>
